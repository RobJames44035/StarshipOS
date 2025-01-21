/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8284944
 * @requires vm.compiler2.enabled
 * @summary triggers the loop optimization phase `LoopOptsCount` many times
 * @run main/othervm -Xcomp -XX:-PartialPeelLoop -XX:CompileCommand=compileonly,TestMaxLoopOptsCountReached::test TestMaxLoopOptsCountReached
 */

import java.lang.System.Logger.Level;

public class TestMaxLoopOptsCountReached {

    static Long a = Long.valueOf(42);

    class A {

        static String e(long f, boolean b, String g, Level h, String s,
                        Object... i) {
            return "message" + s + new String() + g;
        }
    }

    public static void main(String[] args) {
        test(null, "", null, null);
        test(null, "", null, null);
    }

    static void test(Integer o, String g, String name, Object obj) {
        for (Level q : Level.values())
            for (Level r : Level.values())
                A.e(a.longValue(), q != Level.OFF, g, null, null);
        for (Level q : Level.values())
            for (Level r : Level.values())
                A.e(a.longValue(), q != Level.OFF, g, null, null);
        for (Level q : Level.values()) {
            for (Level r : Level.values()) {
                String msg = q + "message";
                String val =
                        (q != Level.OFF || name != msg)
                                ? A.e(a.longValue(), q != Level.OFF, g, null, null, "foo")
                                : null;
            }
            for (Level r : Level.values()) {
                String msg = q + "message";
                String val =
                        (q != Level.OFF || name != msg)
                                ? A.e(a.longValue(), q != Level.OFF, g, null, null, "foo")
                                : null;
            }
        }
        for (Level q : Level.values()) {
            for (Level r : Level.values()) {
                String msg = q + "message";
                String val =
                        (q != Level.OFF || name != msg)
                                ? A.e(a.longValue(), q != Level.OFF, g, null, null, "foo")
                                : null;
            }
            for (Level r : Level.values()) {
                String msg = q + "message";
                String val =
                        (q != Level.OFF || name != msg)
                                ? A.e(a.longValue(), q != Level.OFF, g, null, null, "foo")
                                : null;
            }
        }
        for (Level q : Level.values()) {
            for (Level r : Level.values()) {
                String msg = q + "message";
                String val =
                        (q != Level.OFF || name != msg)
                                ? A.e(a.longValue(), q != Level.OFF, g, null, null, "foo")
                                : null;
            }
            for (Level r : Level.values())
                ;
        }
        for (Level q : Level.values()) {
            for (Level r : Level.values())
                ;
            for (Level r : Level.values())
                ;
        }
        for (Level q : Level.values()) {
            for (Level r : Level.values()) {
                String msg = q + "message";
                String val =
                        (q != Level.OFF || name != msg)
                                ? A.e(a.longValue(), q != Level.OFF, g, null, null, "foo")
                                : null;
            }
            for (Level r : Level.values())
                ;
        }
        for (Level q : Level.values()) {
            for (Level r : Level.values())
                ;
            for (Level r : Level.values())
                ;
        }
    }
}
