/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8021567
 * @summary Javac doesn't report "java: reference to method is ambiguous" any more
 */

public class T8021567b {

    interface SAM {
        int m();
    }

    public static void main(String argv[]) {
        test();
    }

    static boolean test() {
        final int i = 0;
        SAM s = () -> i;
        return (s.m() == 0);
    }
}
