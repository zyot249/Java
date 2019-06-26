package com.topica.zyot.shyn;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes(value = { "com.topica.zyot.shyn.Getter" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GetterAnnotationProcessor extends AbstractProcessor {
	private static final String NOT_PRIVATE_MSG = "Getter method cannot be private!";
	private static final String NOT_RETURN_MSG = "Getter method cannot return nothing!";
	private static final String VOID_TYPE_NAME = "void";
	private Messager messager;

	@Override
	public void init(ProcessingEnvironment env) {
		messager = env.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		for (TypeElement annotation : annotations) {
			Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
			for (Element element : elements) {
				ExecutableElement method = (ExecutableElement) element;
				Set<Modifier> modifiers = method.getModifiers();
				if (modifiers.contains(Modifier.PRIVATE)) {
					messager.printMessage(Kind.ERROR, NOT_PRIVATE_MSG, element);
					break;
				}
				TypeMirror returnType = method.getReturnType();
				if (returnType.toString().equalsIgnoreCase(VOID_TYPE_NAME)) {
					messager.printMessage(Kind.ERROR, NOT_RETURN_MSG, element);
					break;
				}
			}
		}
		return true;
	}

}
