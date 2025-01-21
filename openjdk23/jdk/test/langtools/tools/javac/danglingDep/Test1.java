/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/**
 * @test
 * @bug 6213430
 * @clean Test1 X RefX
 * @compile/ref=Test1.out -XDrawDiagnostics Test1.java RefX.java
 */
class Test1
{
    void foo() {
        // the following doc comment should not affect the following class declaration
        /**
         * @deprecated
         */
        int x;
    }
}

class X {
}
