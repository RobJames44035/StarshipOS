/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  stale state after speculative attribution round leads to missing classfiles
 */
public class LambdaExprLeadsToMissingClassFilesTest<T> {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond) {
            throw new AssertionError();
        }
    }

    interface SAM1<X> {
        X m(X t, String s);
    }

    interface SAM2 {
        void m(String s, int i);
    }

    interface SAM3<X> {
        X m(X t, String s, int i);
    }

    void call(SAM1<T> s1) { assertTrue(true); }

    void call(SAM2 s2) { assertTrue(false); }

    void call(SAM3<T> s3) { assertTrue(false); }

    public static void main(String[] args) {
        LambdaExprLeadsToMissingClassFilesTest<StringBuilder> test =
                new LambdaExprLeadsToMissingClassFilesTest<>();

        test.call((builder, string) -> { builder.append(string); return builder; });
        assertTrue(assertionCount == 1);
    }
}
