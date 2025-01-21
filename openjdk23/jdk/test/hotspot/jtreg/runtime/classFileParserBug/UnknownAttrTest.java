/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8207944
 * @summary Unknown attribute erroneously causes ClassFormatError exception.
 * @compile UnknownAttr.jcod
 * @run main UnknownAttrTest
 */

// Test that an unknown class attribute is ignored and no exception is thrown.
public class UnknownAttrTest {
    public static void main(String args[]) throws Throwable {

        System.out.println("Regression test for bug 8207944");
        try {
            Class newClass = Class.forName("UnknownAttr");
        } catch (java.lang.Throwable e) {
            throw new RuntimeException(
                "Unexpected exception: " + e.getMessage());
        }
    }
}
