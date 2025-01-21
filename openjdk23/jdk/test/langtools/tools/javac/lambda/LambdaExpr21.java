/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006684
 * @summary Compiler produces java.lang.VerifyError: Bad type on operand stack
 * @run main LambdaExpr21
 */
public class LambdaExpr21 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface SAM {
        void foo();
    }

    static class Checker {
        Checker(boolean cond) {
            assertTrue(cond);
        }
    }

    static {
        SAM s = ()-> { new Checker(true) { }; };
        s.foo();
    }

    static void test(){
        SAM s = ()-> { new Checker(true) { }; };
        s.foo();
    }

    static SAM s = ()-> { new Checker(true) { }; };

    public static void main(String[] args) {
        test();
        s.foo();
        assertTrue(assertionCount == 3);
    }
}
