package com.topica.zyot.shyn;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes(value = {"com.topica.zyot.shyn.FinalArguments"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class FinalArgumentsProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment env) {
        filer = env.getFiler();
        messager = env.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> e2s = roundEnvironment.getElementsAnnotatedWith(annotation);
            for (Element e2 : e2s) {
                if (e2.getKind() == ElementKind.METHOD || e2.getKind() == ElementKind.CONSTRUCTOR) {
                    ExecutableElement method = (ExecutableElement) e2;
                    List<? extends VariableElement> args = method.getParameters();
                    for (VariableElement variableElement : args) {
                        Set<Modifier> modifiers = variableElement.getModifiers();
                        if (!modifiers.contains(Modifier.FINAL))
                            messager.printMessage(Kind.ERROR, "All the arguments must be final!", e2);
                    }
                } else {
                    messager.printMessage(Kind.ERROR, "@FinalArguments using for method and constructor!", e2);
                }
            }
        }
        return true;
    }
}
