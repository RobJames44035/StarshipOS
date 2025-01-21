/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package myPkg1;

public class SuperClazz {
    public static void testParent() {
        System.out.println("SuperClazz: entering testParent()");

        // The line below will be used to check for successful class transformation
        System.out.println("parent-transform-check: this-should-be-transformed");
    }
}
