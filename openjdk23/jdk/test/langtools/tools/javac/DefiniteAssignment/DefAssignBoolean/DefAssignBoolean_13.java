/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4262168 6348328
 * @summary Verify that the valid boolean expression compiles.
 * @author maddox
 *
 * @run compile DefAssignBoolean_13.java
 */

public class DefAssignBoolean_13 {

    public static void main(String[] args) {

        boolean b1, b2;
        boolean r = false;
        boolean f = false;
        boolean t = true;

        if ((t && (b1 = t)) ? f : t || (b1 = f));
        else
            r = b1;

    }
}
