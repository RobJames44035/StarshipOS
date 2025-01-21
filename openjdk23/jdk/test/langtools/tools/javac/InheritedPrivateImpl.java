/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4641634
 * @summary inherited private method is considered an implementation of interface method
 * @author gafter
 *
 * @compile InheritedPrivateImpl.java
 */

class InheritedPrivateImpl {

    interface I {
        public void foo();
    }

    static class C1 {
        private void foo() {}
    }

    static abstract class C2 extends C1 implements I {
    }
}
