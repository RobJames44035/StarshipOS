/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4904675 4989669
 * @summary crash in annotation class file reader
 * @author gafter
 *
 * @compile OverrideCheck.java
 */

class OverrideCheck {
    static class A {
        public void f() {}
    }

    static class B extends A {
        @Override
        public void f() {}
    }
}
