/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package p7;

// Only use classes in unsafe and mVI modules with no
// direct or indirect dependency on sun.misc.Unsafe
public class Main {
    public static void main(String... args) {
        p6.safe.Lib.doit();
        org.safe.Lib.doit();
    }
}
