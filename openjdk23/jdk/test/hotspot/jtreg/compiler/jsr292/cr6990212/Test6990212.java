/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6990212
 * @summary JSR 292 JVMTI MethodEnter hook is not called for JSR 292 bootstrap and target methods
 *
 * @run main compiler.jsr292.cr6990212.Test6990212
 */

package compiler.jsr292.cr6990212;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

interface intf {
    public Object target();
}

public class Test6990212 implements intf {
    public Object target() {
        return null;
    }

    public static void main(String[] args) throws Throwable {
        // Build an interface invoke and then invoke it on something
        // that doesn't implement the interface to test the
        // raiseException path.
        MethodHandle target = MethodHandles.lookup().findVirtual(intf.class, "target",  MethodType.methodType(Object.class));
        try {
            target.invoke(new Object());
        } catch (ClassCastException cce) {
            // everything is ok
            System.out.println("got expected ClassCastException");
        }
    }
}
