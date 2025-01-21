/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class TwrForVariable2 implements AutoCloseable {
    public static void meth() {
        TwrForVariable2 v = new TwrForVariable2();
        TwrForVariable3[] v2 = new TwrForVariable3[1];
        TwrForVariable3[][] v3 = new TwrForVariable3[1][1];

        try (final v) {
            fail("no modifiers before variables");
        }
        try (@Deprecated v) {
            fail("no annotations before variables");
        }
        try (v;;) {
            fail("illegal double semicolon");
        }
        try ((v)) {
            fail("parentheses not allowed");
        }
        try (v2[0]) {
            fail("array access not allowed");
        }
        try (v3[0][0]) {
            fail("array access not allowed");
        }
        try (args.length == 0 ? v : v) {
            fail("general expressions not allowed");
        }
        try ((TwrForVariable2)null) {
            fail("null as variable is not allowed");
        }
    }

    static void fail(String reason) {
        throw new RuntimeException(reason);
    }

    public void close() {
    }

}
