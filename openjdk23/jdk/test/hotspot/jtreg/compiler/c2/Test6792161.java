/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 6792161
 * @summary assert("No dead instructions after post-alloc")
 *
 * @run main/othervm/timeout=600 -Xcomp -XX:-TieredCompilation -XX:+IgnoreUnrecognizedVMOptions -XX:MaxInlineSize=120 compiler.c2.Test6792161
 */

package compiler.c2;

import java.lang.reflect.Constructor;

public class Test6792161 {
    static Constructor test(Class cls) throws Exception {
        Class[] args= { String.class };
        try {
            return cls.getConstructor(args);
        } catch (NoSuchMethodException e) {}
        return cls.getConstructor(new Class[0]);
    }
    public static void main(final String[] args) throws Exception {
        try {
            for (int i = 0; i < 100000; i++) {
                Constructor ctor = test(Class.forName("compiler.c2.Test6792161"));
            }
        } catch (NoSuchMethodException e) {}
    }
}
