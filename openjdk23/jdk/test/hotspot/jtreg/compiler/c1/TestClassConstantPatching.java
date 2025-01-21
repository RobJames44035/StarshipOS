/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8282218
 *
 * @run main/othervm -Xcomp -XX:TieredStopAtLevel=1
 *                   -XX:CompileCommand=compileonly,compiler.c1.TestClassConstantPatching$Test::run
 *                      compiler.c1.TestClassConstantPatching
 */
package compiler.c1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class TestClassConstantPatching {
    public static int COUNTER = 0;

    static class CustomLoader extends ClassLoader {
        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if (name.startsWith(Test.class.getName())) {
                Class<?> c = findLoadedClass(name);
                if (c != null) {
                    return c;
                }
                try {
                    COUNTER++; // update counter on loading

                    InputStream in = getSystemResourceAsStream(name.replace('.', File.separatorChar) + ".class");
                    byte[] buf = in.readAllBytes();
                    return defineClass(name, buf, 0, buf.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
            return super.loadClass(name, resolve);
        }
    }

    public static class Test {
        static class T {}

        public static Class<?> run(boolean cond) {
            int before = COUNTER;
            Class<?> c = null;
            if (cond) {
                c = T.class;
            }
            int after = COUNTER;
            if (cond && before == after) {
                throw new AssertionError("missed update");
            }
            return c;
        }
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        ClassLoader cl = new CustomLoader();

        Class.forName(TestClassConstantPatching.class.getName(), true, cl); // preload counter holder class

        Class<?> test = Class.forName(Test.class.getName(), true, cl);

        Method m = test.getDeclaredMethod("run", boolean.class);

        m.invoke(null, false);
        m.invoke(null, true);

        System.out.println("TEST PASSED");
    }
}

