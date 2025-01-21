/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug     7181320
 * @summary javac NullPointerException for switch labels with cast to String expressions
 * @compile CondExprInCaseLabel1.java
 */

public class CondExprInCaseLabel1 {
    public static void main(String [] args) {
        final boolean cond = true;
        switch (args[0]) {
            case cond ? (String)"hello" : "world":
                break;
        }
    }
}
