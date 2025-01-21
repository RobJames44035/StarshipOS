/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7038363
 * @summary cast from object to primitive should be for source >= 1.7
 * @compile CastObjectToPrimitiveTest.java
 */

class CastObjectToPrimitiveTest {
    void m() {
        Object o = 42;
        int i = (int) o;
    }
}
