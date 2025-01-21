/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @bug 4262168
 * @summary Verify that certain unverifiable boolean expressions fail DA test.
 * @author maddox
 *
 * @run compile/fail DefAssignBoolean_15.java
 */

public class DefAssignBoolean_15 {

    public static void main(String[] args) {

        int i = 777, j;
        boolean b;

        if (b = i > 0 && (j = -1) < 0)
            if (j != -1)
                ;

    }
}
