/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 6955504 6992121
 * @summary Test the StringBuilder.ensureCapacity() with negative minimumCapacity
 *    and append() method with negative length input argument.
 *    Also, test the StringBuffer class.
 */

import java.util.ArrayList;
import java.util.Vector;

public class EnsureCapacity {
    public static void main(String[] args) {
        testStringBuilder();
        testStringBuffer();
    }

    private static void checkCapacity(int before, int after) {
        if (before != after) {
            throw new RuntimeException("capacity is expected to be unchanged: " +
                "before=" + before + " after=" + after);
        }
    }

    private static void testStringBuilder() {
        StringBuilder sb = new StringBuilder("abc");
        int cap = sb.capacity();

        // test if negative minimumCapacity
        sb.ensureCapacity(Integer.MIN_VALUE);
        checkCapacity(cap, sb.capacity());

        try {
            char[] str = {'a', 'b', 'c', 'd'};
            // test if negative length
            sb.append(str, 0, Integer.MIN_VALUE + 10);
            throw new RuntimeException("IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
        }
    }

    private static void testStringBuffer() {
        StringBuffer sb = new StringBuffer("abc");
        int cap = sb.capacity();

        // test if negative minimumCapacity
        sb.ensureCapacity(Integer.MIN_VALUE);
        checkCapacity(cap, sb.capacity());

        try {
            char[] str = {'a', 'b', 'c', 'd'};
            // test if negative length
            sb.append(str, 0, Integer.MIN_VALUE + 10);
            throw new RuntimeException("IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
        }
    }
}
