/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006582 8008658
 * @summary javac should generate method parameters correctly.
 * @build MethodParametersTester ClassFileVisitor ReflectionVisitor
 * @compile -parameters LocalClassTest.java
 * @run main MethodParametersTester LocalClassTest LocalClassTest.out
 */

class LocalClassTest {
    void foo() {
        class Local_default_constructor {
            public void foo() {}
            public void foo(int m, int nm) {}
        }
        class Local_has_constructor {
            public Local_has_constructor() {}
            public Local_has_constructor(int a, int ba) {}
            public void foo() {}
            public void foo(int m, int nm) {}
        }
        new LocalClassTest().foo();
    }

    void test(final int i) {
        class CapturingLocal {
            CapturingLocal(final int j) {
               this(new Object() { void test() { int x = i; } });
            }
            CapturingLocal(Object o) { }
        }
        new CapturingLocal(i) { };
    }
}



