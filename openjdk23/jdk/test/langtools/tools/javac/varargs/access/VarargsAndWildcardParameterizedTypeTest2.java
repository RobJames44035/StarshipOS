/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8075520
 * @summary Varargs access check mishandles capture variables
 * @compile             VarargsAndWildcardParameterizedTypeTest2.java
 * @compile --release 8 VarargsAndWildcardParameterizedTypeTest2.java
 */

class VarargsAndWildcardParameterizedTypeTest2 {
    interface I {
        <T> void m(T... t);
    }

    interface Box<T> {
        T get();
    }

    void m(I i, Box<? extends Number> b) {
        i.m(b.get());
    }
}
