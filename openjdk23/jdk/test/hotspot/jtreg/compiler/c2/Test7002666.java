/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 7002666
 * @summary eclipse CDT projects crash with compressed oops
 *
 * @run main/othervm -Xbatch
 *      -XX:CompileCommand=compileonly,compiler.c2.Test7002666::test
 *      -XX:CompileCommand=compileonly,java.lang.reflect.Array::*
 *      compiler.c2.Test7002666
 */

package compiler.c2;
/*
 * This will only reliably fail with a fastdebug build since it relies
 * on seeing garbage in the heap to die.  It could be made more
 * reliable in product mode but that would require greatly increasing
 * the runtime.
 */

public class Test7002666 {
    public static void main(String[] args) {
        for (int i = 0; i < 25000; i++) {
            Object[] a = test(Test7002666.class, new Test7002666());
            if (a[0] != null) {
                // The element should be null but if it's not then
                // we've hit the bug.  This will most likely crash but
                // at least throw an exception.
                System.err.println(a[0]);
                throw new InternalError(a[0].toString());

            }
        }
    }
    public static Object[] test(Class c, Object o) {
        // allocate an array small enough to be trigger the bug
        Object[] a = (Object[])java.lang.reflect.Array.newInstance(c, 1);
        return a;
    }
}
