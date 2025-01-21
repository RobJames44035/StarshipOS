/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8193214
 * @summary Verify annotations without processors warning not given for base module annotations.
 * @library /tools/javac/lib
 * @modules java.compiler
 * @build JavacTestingAbstractProcessor TestAnnotationsWithoutProcessors
 * @compile/ref=empty.out -XDrawDiagnostics -Xlint:processing,-options -processor TestAnnotationsWithoutProcessors --release 8 TestAnnotationsWithoutProcessors.java
 * @compile/ref=empty.out -XDrawDiagnostics -Xlint:processing -processor TestAnnotationsWithoutProcessors TestAnnotationsWithoutProcessors.java
 */

import java.lang.annotation.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;

/**
 * Use various annotations in java.base.
 */
@SuppressWarnings("unchecked")
public class TestAnnotationsWithoutProcessors extends JavacTestingAbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

    @SafeVarargs
    @Deprecated
    public static void main(String... args) {
        return;
    }

    @FunctionalInterface
    interface OneMethod {
        String method();
    }

    @Native
    public double TAU = 2.0 * Math.PI;

    @Documented
    @Inherited
    @Repeatable(TestAnnotationTypes.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface TestAnnotationType {
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface TestAnnotationTypes {
        TestAnnotationType[] value();
    }
}
