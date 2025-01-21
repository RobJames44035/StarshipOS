/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4262168
 * @summary Verify that certain unverifiable boolean expressions fail DA test.
 * @author maddox
 *
 * @run compile/fail DefAssignBoolean_5.java
 */

public class DefAssignBoolean_5 {

    public static void main(String[] args) {

                boolean b3, b4;
                boolean r = false;
                boolean t = true;

                if ((t && (b3 = r)) != (t && (b3 = t)))
                    r = b3;
    }
}
