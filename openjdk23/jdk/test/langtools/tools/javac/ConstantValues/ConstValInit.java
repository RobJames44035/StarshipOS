/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4253590
 * @summary Verify that non-static constant fields are initialized correctly.
 *          Also, while a runtime initialization is required, verify that
 *          references to such fields via a simple variable are correctly
 *          recognized as constant expressions.
 * @author maddox (adapted from bug report)
 *
 * @run compile ConstValInit.java
 * @run main ConstValInit
 */

public class ConstValInit {

    public final String fnl_str = "Test string";

    public final int fnl_int = 1;

    public static void main(String args[]) throws Exception {

        Class checksClass = Class.forName("ConstValInit");
        ConstValInit checksObject = (ConstValInit)(checksClass.newInstance());
        String reflected_fnl_str = (String)checksClass.getField("fnl_str").get(checksObject);
        if (!checksObject.fnl_str.equals(reflected_fnl_str)) {
            throw new Exception("FAILED: ordinary and reflected field values differ");
        }

    }

    void foo() {
        // Statement below will not compile if fnl_int is not recognized
        // as a constant expression.
        switch (1) {
            case fnl_int: break;
        }
    }

}
