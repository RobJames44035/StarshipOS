/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.share.test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import nsk.share.Failure;
import nsk.share.TestFailure;

import static java.lang.String.format;

public class TestUtils {
    /**
     * @throws nsk.share.Failure with given error message
     *
     */
    public static void testFailed(Object errorMessage) {
        throw new Failure(errorMessage.toString());
    }

    /**
     * Checks that expr is true
     *
     * @throws nsk.share.Failure
     *          when expr is false
     */
    public static void assertTrue(boolean expr, Object errorMessage) {
        if (!expr)
            testFailed(errorMessage);
    }

    /**
     * Checks that obj is not null
     *
     * @throws nsk.share.Failure
     *          when obj is null
     */
    public static void assertNotNull(Object obj, Object errorMessage) {
        assertTrue(obj != null, errorMessage);
    }

    /**
     * Checks that obj1 equal obj2
     *
     * @throws nsk.share.Failure
     *          when obj1 is not equal obj2
     */
    public static void assertEquals(Object obj1, Object obj2, Object errorMessage) {
        assertTrue(obj1.equals(obj2), new LazyFormatString("%s: [%s] != [%s]", errorMessage,  obj1, obj2));
    }

    public static <T> void assertNotInCollection(Collection<T> list, T value) {
        assertTrue(! list.contains(value), new LazyFormatString("Internal error: %s is in collection %s", value, list));
    }

    public static <T> void assertInCollection(Collection<T> list, T value) {
        assertTrue(list.contains(value), new LazyFormatString("Internal error: %s is not in collection %s", value, list));
    }

    public static void assertEquals(int i1, int i2) {
        if (i1 != i2) {
            throw new TestFailure(
                    format("Check failed: %d != %d", i1, i2));
        }
    }

    public static void fail(String msg) {
        throw new TestFailure(msg);
    }

    public static void assertEquals(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return;
        }

        if (s1 != null && s1.equals(s2)) {
            return;
        }

        throw new TestFailure(format("Failed: %s != %s", s1, s2));
    }

    /**
     * Check that obj is an instance of a class c.
     *
     * @param obj
     * @param c
     */
    public static void assertExactClass(Object obj, Class c) {
        if (obj.getClass() != c) {
            throw new TestFailure(format("Exact class doesn't match: expected: %s; actual: %s",
                                         c.getName(), obj.getClass().getName()));
        }
    }
    /**
     * @throws nsk.share.Failure (given exception is set as Failure cause)
     *
     */
    public static void unexpectedException(Throwable exception) {
        throw new Failure("Unexpected exception: " + exception, exception);
    }

    public static <T> T[] concatArrays(T[] a1, T[] a2) {
        T[] result = Arrays.copyOf(a1, a1.length + a2.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }

    public static <T> T[] concatArrays(T[] a1, T[] a2, T[] a3) {
        T[] result = Arrays.copyOf(a1, a1.length + a2.length + a3.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        System.arraycopy(a3, 0, result, a1.length + a2.length, a3.length);
        return result;
    }

    public static <T> T[] concatArrays(T a1, T[] a2) {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(a1.getClass(), 1 + a2.length);
        result[0] = a1;
        System.arraycopy(a2, 0, result, 1, a2.length);
        return result;
    }

    public static <T> T[] cdr(T[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }
}
