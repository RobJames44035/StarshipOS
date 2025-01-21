/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4106051
 * @summary A helper method and cache cell should be defined for
 * every class containing a given class literal, ignoring any
 * equivalent members that may be inherited.
 *
 * @compile ClassLiteralHelperContext.java
 * @run main ClassLiteralHelperContext
 */

/*
 * A failing test will get an access error when run.
 */

import p1.*;

public class ClassLiteralHelperContext extends SuperClass {
    Class c = C.class;

    public static void main(String[] args) {
        // Evaluation of literal 'C.class' will fail
        // during initialization if the (package private)
        // cache cell or helper method is inherited.
        ClassLiteralHelperContext x = new ClassLiteralHelperContext();
        System.out.println(x.c);
    }
}
