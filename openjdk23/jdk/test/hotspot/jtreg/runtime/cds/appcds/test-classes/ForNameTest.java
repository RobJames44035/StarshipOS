/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

public class ForNameTest {
    public static void main(String[] args) throws Throwable {
        // Hello is on the bootclasspath. The defining classloader is
        // the NULL classloader. See ../ClassLoaderTest.java
        Class c = Class.forName("Hello");
        ClassLoader cl = c.getClassLoader();
        if (cl != null) {
            throw new RuntimeException(
                "Test Failed. Wrong classloader is used. Expect the NULL classloader.");
        }

        WhiteBox wb = WhiteBox.getWhiteBox();
        if (wb.isSharedClass(c)) {
            System.out.println("As expected, Hello.class is in shared space.");
        } else {
            throw new java.lang.RuntimeException("Hello.class must be in shared space.");
        }
    }
}
