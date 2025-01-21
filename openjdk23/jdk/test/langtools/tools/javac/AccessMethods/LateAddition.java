/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4098093
 * @summary Compiler must properly handle access methods
 * created after their class has been checked.
 *
 * @compile LateAddition.java
 */

/*
 * This is the example submitted in the bug report.
 * It should compile successfully.
 * Future changes to the compiler may affect the timing
 * of the creation of access methods, making it an unreliable
 * test of the condition described in the summary.
 */

public class LateAddition {
    public int f() {
        class Local {
            private int i = 5;
        }
        return (new Local()).i;
    }
}
