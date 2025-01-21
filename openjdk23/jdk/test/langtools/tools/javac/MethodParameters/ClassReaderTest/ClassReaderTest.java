/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8007720 8177486
 * @summary class reading of named parameters
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @compile -parameters ClassReaderTest.java MethodParameterProcessor.java
 * @compile/process/ref=ClassReaderTest.out -proc:only -processor MethodParameterProcessor ClassReaderTest ClassReaderTest$I ClassReaderTest$E
 */

import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

public class ClassReaderTest {

    @Retention(RUNTIME)
    @interface RuntimeAnnoOne {
        int value() default 0;
    }

    @Retention(RUNTIME)
    @interface RuntimeAnnoTwo {
        int value() default 0;
    }

    @Retention(CLASS)
    @interface ClassAnno {
        int value() default 0;
    }

    @MethodParameterProcessor.ParameterNames
    void f(
            @RuntimeAnnoOne(1) @RuntimeAnnoTwo(2) @ClassAnno(3) int a,
            @RuntimeAnnoOne(4) @RuntimeAnnoTwo(5) @ClassAnno(6) String b) {}

    class I {
        @MethodParameterProcessor.ParameterNames
        I(@ClassAnno(7) int d, @RuntimeAnnoOne(8) String e, Object o) {}
    }

    enum E {
        ONE(42, "");

        @MethodParameterProcessor.ParameterNames
        E(int x, @RuntimeAnnoOne(9) String s) {}
    }
}
