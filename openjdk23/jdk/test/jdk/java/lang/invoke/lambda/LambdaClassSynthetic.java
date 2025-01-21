/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8008941
 * @summary Generated Lambda implementing class should be synthetic
 */

import java.lang.reflect.Modifier;
import java.io.Serializable;

public class LambdaClassSynthetic {

    interface I {
        void m();
    }

    interface Iser extends Serializable {
        void m();
    }

    static void assertTrue(boolean cond) {
        if (!cond)
            throw new AssertionError();
    }

    public static void main(String[] args) throws Exception {
        new LambdaClassFinal().test();
    }

    void test() throws Exception {
        I lam = () -> { };
        assertTrue(lam.getClass().isSynthetic());
        Iser slam = () -> { };
        assertTrue(slam.getClass().isSynthetic());
    }
}
