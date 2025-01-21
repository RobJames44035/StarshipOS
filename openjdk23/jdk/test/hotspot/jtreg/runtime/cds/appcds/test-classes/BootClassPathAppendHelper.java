/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

public class BootClassPathAppendHelper {
    public static void main(String[] args) throws ClassNotFoundException {
        Class cls = Class.forName("Hello");

        if (cls == null) {
            throw new java.lang.RuntimeException("Cannot find Hello.class");
        }

        WhiteBox wb = WhiteBox.getWhiteBox();
        if (!wb.isSharedClass(cls)) {
            System.out.println("Hello.class is not in shared space as expected.");
        } else {
            throw new java.lang.RuntimeException("Hello.class shouldn't be in shared space.");
        }
    }
}
