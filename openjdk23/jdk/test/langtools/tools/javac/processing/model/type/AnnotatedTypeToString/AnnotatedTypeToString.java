/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8284220 8342934
 * @summary Tests DeclaredType.toString with type annotations present, for example that '@A
 * Map.Entry' is printed as 'java.util.@A Map.Entry' (and not '@A java.util.Map.Entry' or
 * 'java.util.@A Entry').
 * @library /tools/javac/lib
 * @build AnnotatedTypeToString JavacTestingAbstractProcessor ExpectedToString
 * @compile -processor AnnotatedTypeToString -proc:only Test.java
 */

import p.ExpectedToString;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Verify that the toString representation of the types of elements annotated with {@code
 * ExpectedToString} matches the expected string representation in the annotation.
 */
@SupportedAnnotationTypes("p.ExpectedToString")
public class AnnotatedTypeToString extends JavacTestingAbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        for (Element e : roundEnv.getElementsAnnotatedWith(ExpectedToString.class)) {
            String expected = e.getAnnotation(ExpectedToString.class).value();
            String actual = e.asType().toString();
            if (!expected.equals(actual)) {
                processingEnv
                        .getMessager()
                        .printError(String.format("expected: %s, was: %s", expected, actual), e);
            }
        }
        return false;
    }
}
