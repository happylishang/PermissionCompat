package com.permmision;

import com.google.auto.service.AutoService;
import com.permmision.annotion.ActivityPermmision;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class PermmisionProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ActivityPermmision.class);
        //查询所有的Activity 并根据里面的方法生成相应的回调
        return true;
    }
}
