/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4696927
 * @summary spurious unthrown exception error when throw precedes try-finally
 * @author gafter
 *
 * @compile ThrowBeforeTryFinally.java
 */

public class ThrowBeforeTryFinally {

    static class MyEx extends Exception {}

    public String test() {
        try {
            if (true) throw new MyEx();
            try {
            } finally {
                return null;
            }
        } catch (MyEx ex) {
            return null;
        }
    }
}
