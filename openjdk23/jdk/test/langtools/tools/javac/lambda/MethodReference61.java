/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006763
 * @summary super in method reference used in anonymous class
 */
public class MethodReference61 {
    interface SAM {
        void m();
    }

    static class MyTester {
        public void ifoo() { }
    }

    public static void main(String args[]) {
        MyTester t = new MyTester() {
            SAM s = super::ifoo;
        };
    }
}
