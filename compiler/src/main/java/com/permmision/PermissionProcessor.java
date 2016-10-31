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
import java.util.Arrays;
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
import javax.tools.Diagnostic;

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


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ActivityPermission.class);

        if (!checkIntegrity(roundEnv))
            return false;

        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            List<? extends Element> members = elementUtils.getAllMembers(typeElement);
            for (Element item : members) {
                OnGranted grantedAnnotation = item.getAnnotation(OnGranted.class);
                if (grantedAnnotation != null) {

                    String[] params = grantedAnnotation.value();
                    Element deny = null;
                    Element neverAsk = null;
                    Element showRationale = null;

                    TypeSpec.Builder builder = TypeSpec.classBuilder(element.getSimpleName() + item.getSimpleName().toString() + "$Listener")
                            .addSuperinterface(ParameterizedTypeName.get(ClassName.bestGuess(OnGrantedListener.class.getTypeName()), ClassName.bestGuess(element.getSimpleName().toString())))
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

                    MethodSpec.Builder bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onGranted")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .returns(TypeName.VOID)
                            .addParameter(ClassName.get(typeElement.asType()), "activity");
                    bindViewMethodSpecBuilder.addStatement(String.format("activity.%s()", item.getSimpleName().toString()));
                    builder.addMethod(bindViewMethodSpecBuilder.build());
                    for (Element other : members) {
                        OnDenied tmpOnDenied = other.getAnnotation(OnDenied.class);
                        if (tmpOnDenied != null && Arrays.equals(tmpOnDenied.value(), params)) {
                            deny = other;
                            continue;
                        }

                        OnNeverAsk tmpNeverAsk = other.getAnnotation(OnNeverAsk.class);
                        if (tmpNeverAsk != null && Arrays.equals(tmpNeverAsk.value(), params)) {
                            neverAsk = item;
                            continue;
                        }
                        OnShowRationale tmpShowRationale = other.getAnnotation(OnShowRationale.class);
                        if (tmpShowRationale != null && Arrays.equals(tmpShowRationale.value(), params)) {
                            showRationale = item;
                        }
                    }

                    bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onDenied")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .returns(TypeName.VOID)
                            .addParameter(ClassName.get(typeElement.asType()), "activity");
                    if (deny != null) {
                        bindViewMethodSpecBuilder.addStatement(String.format("activity.%s()", deny.getSimpleName().toString()));
                    }
                    builder.addMethod(bindViewMethodSpecBuilder.build());

                    bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onNeverAsk")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .returns(TypeName.VOID)
                            .addParameter(ClassName.get(typeElement.asType()), "activity");
                    if (neverAsk != null) {
                        bindViewMethodSpecBuilder.addStatement(String.format("activity.%s()", neverAsk.getSimpleName().toString()));
                    }
                    builder.addMethod(bindViewMethodSpecBuilder.build());

                    bindViewMethodSpecBuilder = MethodSpec.methodBuilder("onShowRationale")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class)
                            .returns(TypeName.VOID)
                            .addParameter(ClassName.get(typeElement.asType()), "activity");
                    if (showRationale != null) {
                        bindViewMethodSpecBuilder.addStatement(String.format("activity.%s()", showRationale.getSimpleName().toString()));
                    }
                    builder.addMethod(bindViewMethodSpecBuilder.build());


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

    private boolean checkIntegrity(RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ActivityPermission.class);

        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            List<? extends Element> members = elementUtils.getAllMembers(typeElement);
            for (Element item : members) {
                OnGranted grantedAnnotation = item.getAnnotation(OnGranted.class);
                if (grantedAnnotation != null) {
                    String[] params = grantedAnnotation.value();
                    Element deny = null;
                    Element neverAsk = null;
                    Element showRationale = null;
                    for (Element other : members) {
                        OnDenied tmpOnDenied = other.getAnnotation(OnDenied.class);
                        if (tmpOnDenied != null && Arrays.equals(tmpOnDenied.value(), params)) {
                            deny = other;
                            continue;
                        }
                        OnNeverAsk tmpNeverAsk = other.getAnnotation(OnNeverAsk.class);
                        if (tmpNeverAsk != null && Arrays.equals(tmpNeverAsk.value(), params)) {
                            neverAsk = item;
                            continue;
                        }
                        OnShowRationale tmpShowRationale = other.getAnnotation(OnShowRationale.class);
                        if (tmpShowRationale != null && Arrays.equals(tmpShowRationale.value(), params)) {
                            showRationale = item;
                        }
                    }

                    if (deny == null) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "need OnDenied func ", element);
                        return false;
                    }

                    if (neverAsk == null) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "need OnNeverAsk func ", element);
                        return false;
                    }

                    if (showRationale == null) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "need OnShowRationale func ", element);
                        return false;
                    }
                }
            }

        }
        return true;
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