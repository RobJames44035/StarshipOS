/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4881179 4883239
 * @summary Rule for semantics of ?: in the presence of generics and generic class Class
 * @author gafter
 *
 * @compile  Conditional.java
 */

package conditional;

import java.io.Serializable;

interface I {}
interface J {}
class A implements I, J {}
class B implements I, J {}
class C extends B {}

class Conditional {
    static boolean cond = String.class.getName().length() == 1;
    public static void main(String[] args) {
        Class c = cond ? A.class : B.class;
        Class<?> d = cond ? A.class : B.class;

        Class<? extends B> e = cond ? B.class : C.class;
    }

    void f(A a, B b) {
        I i = cond ? a : b;
        J j = cond ? a : b;
    }

    // required for compatibility
    Class g(Class a) {
        return cond ? a : B.class;
    }

    // required for compatibility
    byte[] h(byte[] a, byte[] b) {
        return cond ? a : b;
    }

    // This one is hard because of the recursive F-bounds
    // The naive result is the infinite type
    // Class<? extends Number&Comparable<? extends Number&Comparable<? extends
    // ...
    Class<? extends Comparable<?>> c =
        cond ? Integer.class : Float.class;

    Comparable<?> o =
        cond ? true : 3;

    /*

    // See 4942040
    void f(Cloneable a, int[] b) {
        Cloneable x = cond ? a : b;
    }
    void f(Serializable a, int[] b) {
        Serializable x = cond ? a : b;
    }

    // See 4941882
    void f(float[] a, int[] b) {
        Serializable x = cond ? a : b;
    }
    */
}
