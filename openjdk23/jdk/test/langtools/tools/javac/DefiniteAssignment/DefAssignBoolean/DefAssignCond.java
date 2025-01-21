/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4354393 4402005
 * @summary Verify correct treatment and code gen for ?: definite assignment
 * @author gafter
 *
 * @compile DefAssignCond.java
 * @run main DefAssignCond
 */

public class DefAssignCond {
    public static void main (String[] args) {
        boolean t = true, f = false, b1, b2;
        if (t ? (b1 = t) : false)
            t = b1;
        if (f ? true : (b2 = f)) ;
        else
            f = b2;
    }
}
