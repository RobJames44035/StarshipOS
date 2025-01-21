/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4262168
 * @summary Verify that certain unverifiable boolean expressions fail DA test.
 * @author maddox
 *
 * @run compile/fail DefAssignBoolean_9.java
 */

public class DefAssignBoolean_9 {

    public static void main(String[] args) {

                int i, j;
                boolean [] a = new boolean [10];

                if (a[1] &= a[0] && (j = -1) < 0)
                    if (j != -1)
                        ;
    }
}
