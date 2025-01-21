/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that recursion from doubly nested lambda is handled correctly
 */

public class LambdaExpr14 {

    interface SAM {
       SAM invoke();
    }

    static SAM local;

    public static void main(String[] args) {
        local = () -> () -> local.invoke();
        local.invoke().invoke(); // Not a recursive lambda - exec should terminate
    }
}
