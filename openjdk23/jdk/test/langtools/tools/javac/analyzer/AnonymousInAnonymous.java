/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8197439
 * @summary Check anonymous class in anonymous class, where the nested one becomes
 *          unresolvable with lambda conversion.
 * @compile -XDfind=lambda -Werror AnonymousInAnonymous.java
 */

public class AnonymousInAnonymous {
    static void s(I1 i) {}
    static {
        s(
            new I1() {
                public I2 get() {
                    return new I2() {
                    };
                }
            });
    }
    public static interface I1 {
        public static class I2 { }
        public I2 get();
    }
}
