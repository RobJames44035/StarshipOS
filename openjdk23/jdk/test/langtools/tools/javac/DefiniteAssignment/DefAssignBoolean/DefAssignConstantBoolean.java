/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4304751
 * @summary Verify correct treatment of boolean constant expressions with '|'. "&', and '^'.
 * @author maddox
 *
 * @run compile DefAssignConstantBoolean.java
 */

public class DefAssignConstantBoolean {
    public static void main(String args[]) {
        boolean b;
        if (true | false) ;
        else
            if (b) ;        // case '|'
        if (true & true) ;
        else
            if (b) ;        // case '&'
        if (true ^ false) ;
        else
            if (b) ;        // case '^'
    }
}
