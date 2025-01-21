/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8075799
 *
 * @summary Extraneous access checks implemented by javac
 * @compile CtorAccessBypassTest.java
 * @run main CtorAccessBypassTest
 *
 */

public class CtorAccessBypassTest {
    public static void main(String[] args) {
        new CtorAccessBypassTest_01<Object>(null) {};
        new CtorAccessBypassTest_01<>(null) {};
    }
}

class CtorAccessBypassTest_01<T> {
    private class Private {}
      CtorAccessBypassTest_01(Private p) {
    }
}
