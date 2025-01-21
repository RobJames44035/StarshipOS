/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4312781 4881179 4949543
 * @summary Verify that both branches of a conditional expression must agree in type.
 * @author maddox
 *
 * @compile                  ConditionalArgTypes_2.java
 */

// This case was working before -- controlling expression is not a constant.

class ConditionalArgTypes_2 {
    public static void main (String [] args) {
        boolean b = true;
        System.out.println(b?0:false);
    }
}
