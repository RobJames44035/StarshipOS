/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8202597
 * @summary javac is not inducing a notional interface if Object appears in an intersection type
 * @compile NotionalInterfaceNotBeingInducedTest.java
 */

class NotionalInterfaceNotBeingInducedTest {
    interface I {}
    interface J { void foo(); }

    public void test() {
        Object o1 = (I & J) System::gc;
        Object o2 = (J) System::gc;
        Object o3 = (Object & J) System::gc;
        Object o4 = (Object & I & J) System::gc;
    }
}
