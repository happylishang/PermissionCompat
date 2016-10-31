package com.permmision;


import com.annotation.annotation.ActivityPermission;
import com.annotation.annotation.OnDenied;
import com.annotation.annotation.OnGranted;
import com.annotation.annotation.OnGrantedListener;
import com.annotation.annotation.OnNeverAsk;
import com.annotation.annotation.OnShowRationale;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class PermissionProcessor extends AbstractProcessor {

    private Elements elementUtils;

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(OnDenied.class);
        annotations.add(OnGranted.class);
        annotations.add(OnNeverAsk.class);
        annotations.add(OnShowRationale.class);
        return annotations;
    }

    interface State {
        int OnDenied = 1;
        int OnGranted = 2;
        int OnNeverAsk = 3;
        int OnShowRationale = 4;

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ActivityPermission.class);

        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            List<? extends Element> members = elementUtils.getAllMembers(typeElement);

            for (Element item : members) {
                OnGranted grantedAnnotation = item.getAnnotation(OnGranted.class);
                if (grantedAnnotation != null) {
                    TypeSpec.Builder builder = TypeSpec.classBuilder(element.getSimpleName() + item.getSimpleName().toString() + "$Listener")
                            .addSuperinterface(ParameterizedTypeName.get(ClassName.bestGuess(OnGrantedListener.class.getTypeName()), ClassName.bestGuess(element.getSimpleName().toString())))
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

                    MethodSpec.Builder bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onGranted")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .returns(TypeName.VOID)
                            .addParameter(ClassName.get(typeElement.asType()), "activity");
                    builder.addMethod(bindViewMethodSpecBuilder.build());
                    String[] params = grantedAnnotation.value();
                    for (Element element1 : members) {
                        Annotation deniedAnnotation = item.getAnnotation(Annotation.class);
                        int index = -1;
                        if (deniedAnnotation instanceof OnDenied) {
                            index = State.OnDenied;
                        } else if (deniedAnnotation instanceof OnNeverAsk) {
                            index = State.OnNeverAsk;
                        } else if (deniedAnnotation instanceof OnShowRationale) {
                            index = State.OnShowRationale;
                        }
                        if (index > 0) {
                            switch (index) {
                                case State.OnDenied:
                                    OnDenied deny = (OnDenied) deniedAnnotation;
                                    if (deny.value().equals(params)) {
                                        bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onDenied");
                                        bindViewMethodSpecBuilder.addModifiers(Modifier.PUBLIC)
                                                .addAnnotation(Override.class)
                                                .returns(TypeName.VOID)
                                                .addParameter(ClassName.get(typeElement.asType()), "activity");
                                        bindViewMethodSpecBuilder.addStatement(String.format("activity.%s()", element1.getSimpleName().toString()));

                                    }
                                    break;
                                case State.OnNeverAsk:
                                    OnNeverAsk neverAsk = (OnNeverAsk) deniedAnnotation;
                                    if (neverAsk.value().equals(params)) {
                                        bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onNeverAsk");
                                        bindViewMethodSpecBuilder.addModifiers(Modifier.PUBLIC)
                                                .addAnnotation(Override.class)
                                                .returns(TypeName.VOID)
                                                .addParameter(ClassName.get(typeElement.asType()), "activity");
                                        bindViewMethodSpecBuilder.addStatement(String.format("activity.%s()", element1.getSimpleName().toString()));

                                    }
                                    break;
                                case State.OnShowRationale:
                                    OnShowRationale showRationale = (OnShowRationale) deniedAnnotation;
                                    if (showRationale.value().equals(params)) {
                                        bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onShowRationale");
                                        bindViewMethodSpecBuilder.addModifiers(Modifier.PUBLIC)
                                                .addAnnotation(Override.class)
                                                .returns(TypeName.VOID)
                                                .addParameter(ClassName.get(typeElement.asType()), "activity");
                                        bindViewMethodSpecBuilder.addStatement(String.format("activity.%s()", element1.getSimpleName().toString()));

                                    }
                                    break;

                            }
                        }
                    }

                    TypeSpec typeSpec = builder.build();
                    JavaFile javaFile = JavaFile.builder(getPackageName(typeElement), typeSpec).build();
                    try {
                        javaFile.writeTo(processingEnv.getFiler());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

        }
        return true;
    }


    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();

    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }
}