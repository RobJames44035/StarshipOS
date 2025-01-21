/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8068737 8281238
 * @summary Tests ArrayType.toString with type annotations present
 * @modules jdk.compiler/com.sun.tools.javac.code
 * @library /tools/javac/lib
 * @build ArrayTypeToString JavacTestingAbstractProcessor
 * @compile/ref=ArrayTypeToString.out -XDaccessInternalAPI -XDrawDiagnostics -processor ArrayTypeToString -proc:only ArrayTypeToString.java
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;


import com.sun.tools.javac.code.Symbol.VarSymbol;

@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE_USE, ElementType.FIELD })
@interface Foo {
    int value();
}

@SupportedAnnotationTypes("Foo")
public class ArrayTypeToString extends JavacTestingAbstractProcessor {
    @Foo(0) String @Foo(1)[] @Foo(2)[] @Foo(3)[] field;

    public boolean process(Set<? extends TypeElement> tes, RoundEnvironment renv) {
        for (TypeElement te : tes) {
            for (Element e : renv.getElementsAnnotatedWith(te)) {
                String s = ((VarSymbol) e).type.toString();

                // Normalize output by removing whitespace
                s = s.replaceAll("\\s", "");

                // Expected: "java.lang.@Foo(0)String@Foo(1)[]@Foo(2)[]@Foo(3)[]"
                processingEnv.getMessager().printNote(s);
            }
        }
        return true;
    }
}
