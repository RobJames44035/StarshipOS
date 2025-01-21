/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test %E
 * @bug 4094353
 * @summary Verify definite assignment state following one-armed if-statement with constant 'false'.
 * @author William Maddox (maddox)
 *
 * @run compile/fail DefAssignAfterIf_2.java
 */

class DefAssignAfterIf_2 {
    void test () {
        int i;
        if (false) i = 3;
        System.out.println(i);  // ERROR -- 'i' is not definitely assigned
    }
}
