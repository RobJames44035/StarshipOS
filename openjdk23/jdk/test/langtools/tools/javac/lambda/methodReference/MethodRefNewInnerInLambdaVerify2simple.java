/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8038776
 * @summary VerifyError when running successfully compiled java class
 */

import java.util.function.Function;

/**
 * Derived from code by:
 * @author Yawkat
 */
public class MethodRefNewInnerInLambdaVerify2simple {
    public static void main(String[] args) { new MethodRefNewInnerInLambdaVerify2simple().runTest(); }

    private void runTest() {
        Runnable r = (() -> { Sup w = SomeClass::new; } );
    }

    private class SomeClass {
        SomeClass() {  }
    }
}

interface Sup {
  Object get();
}
