/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class TwrForVariable3 implements AutoCloseable {
    public static void meth() {
        TwrForVariable3 v1 = new TwrForVariable3();
        Object v2 = new Object();
        Object v3 = new Object() {
            public void close() {
            }
        };

        try (v2) {
            fail("not an AutoCloseable");
        }
        try (v3) {
            fail("not an AutoCloseable although has close() method");
        }
        try (java.lang.Object) {
            fail("not a variable access");
        }
        try (java.lang) {
            fail("not a variable access");
        }
    }

    static void fail(String reason) {
        throw new RuntimeException(reason);
    }

    public void close() {
    }

}
