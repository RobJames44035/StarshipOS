/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package p;

/**
 * Test interface I, provides default implementations for m() and m(11args).
 */

public interface I {
    default public int m() { return 1; }
    default public int m(byte b, char c, short s, int i, long l,
           Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        return 2;
    }
}
