/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @run main/othervm -Xint
 *                   -XX:CompileCommand=dontinline,*TestGetModifiers.test
 *                   compiler.intrinsics.klass.TestGetModifiers
 */

/*
 * @test
 * @requires vm.compiler1.enabled
 * @library /test/lib
 * @run main/othervm -XX:TieredStopAtLevel=1 -XX:+TieredCompilation
 *                   -XX:CompileCommand=dontinline,*TestGetModifiers.test
 *                   compiler.intrinsics.klass.TestGetModifiers
 */

/*
 * @test
 * @requires vm.compiler2.enabled
 * @library /test/lib
 * @run main/othervm -XX:-TieredCompilation
 *                   -XX:CompileCommand=dontinline,*TestGetModifiers.test
 *                   compiler.intrinsics.klass.TestGetModifiers
 */

package compiler.intrinsics.klass;

import java.lang.reflect.Modifier;
import static java.lang.reflect.Modifier.*;

import jdk.test.lib.Asserts;

public class TestGetModifiers {
    public static class T1 {
    }

    public static final class T2 {
    }

    private static class T3 {
    }

    protected static class T4 {
    }

    class T5 {
    }

    interface T6 {
    }

    static void test(Class cl, int expectedMods) {
        for (int i = 0; i < 100_000; i++) {
            int actualMods = cl.getModifiers();
            if (actualMods != expectedMods) {
                throw new IllegalStateException("Error with: " + cl);
            }
        }
    }

    public static void main(String... args) {
        test(T1.class,                                      PUBLIC | STATIC);
        test(T2.class,                                      PUBLIC | FINAL | STATIC);
        test(T3.class,                                      PRIVATE | STATIC);
        test(T4.class,                                      PROTECTED | STATIC);
        test(new TestGetModifiers().new T5().getClass(),    0);
        test(T6.class,                                      ABSTRACT | STATIC | INTERFACE);

        test(int.class,                                     PUBLIC | ABSTRACT | FINAL);
        test(long.class,                                    PUBLIC | ABSTRACT | FINAL);
        test(double.class,                                  PUBLIC | ABSTRACT | FINAL);
        test(float.class,                                   PUBLIC | ABSTRACT | FINAL);
        test(char.class,                                    PUBLIC | ABSTRACT | FINAL);
        test(byte.class,                                    PUBLIC | ABSTRACT | FINAL);
        test(short.class,                                   PUBLIC | ABSTRACT | FINAL);
        test(void.class,                                    PUBLIC | ABSTRACT | FINAL);
        test(int[].class,                                   PUBLIC | ABSTRACT | FINAL);
        test(long[].class,                                  PUBLIC | ABSTRACT | FINAL);
        test(double[].class,                                PUBLIC | ABSTRACT | FINAL);
        test(float[].class,                                 PUBLIC | ABSTRACT | FINAL);
        test(char[].class,                                  PUBLIC | ABSTRACT | FINAL);
        test(byte[].class,                                  PUBLIC | ABSTRACT | FINAL);
        test(short[].class,                                 PUBLIC | ABSTRACT | FINAL);
        test(Object[].class,                                PUBLIC | ABSTRACT | FINAL);
        test(TestGetModifiers[].class,                      PUBLIC | ABSTRACT | FINAL);

        test(new TestGetModifiers().getClass(),             PUBLIC);
        test(new T1().getClass(),                           PUBLIC | STATIC);
        test(new T2().getClass(),                           PUBLIC | FINAL | STATIC);
        test(new T3().getClass(),                           PRIVATE | STATIC);
        test(new T4().getClass(),                           PROTECTED | STATIC);
    }
}
