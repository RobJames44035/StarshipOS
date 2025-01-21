/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Verify that throwException() can throw an exception
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main ThrowException
 */

import jdk.internal.misc.Unsafe;
import static jdk.test.lib.Asserts.*;

public class ThrowException {
    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.throwException(new TestException());
        } catch (Throwable t) {
            if (t instanceof TestException) {
                return;
            }
            throw t;
        }
        throw new RuntimeException("Did not throw expected TestException");
    }
    static class TestException extends Exception {}
}
