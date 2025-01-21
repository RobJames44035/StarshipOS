/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4399129 6980724
 * @summary Check that assertions compile properly when nested in an interface
 * @author gafter
 * @compile InterfaceAssert.java
 * @run main InterfaceAssert
 */

/*
 * Verify that assertions compile properly within a top-level interface
 */

interface I {
    class C {
        C(){}
        public void test(String s) {
            assert s == "Yup";
        }
    }
}

public class InterfaceAssert {
    public static void main(String[] args) {
        I.C c = new I.C();
        c.test("Yup");
    }
}
