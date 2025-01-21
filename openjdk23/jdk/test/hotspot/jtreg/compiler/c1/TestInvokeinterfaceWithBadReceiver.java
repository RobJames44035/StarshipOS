/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8312909
 * @summary Test monomorphic interface call to with invalid receiver.
 * @modules java.base/jdk.internal.vm.annotation
 * @compile TestInvokeinterfaceWithBadReceiverHelper.jasm
 * @run main/bootclasspath/othervm -XX:CompileCommand=compileonly,TestInvokeinterfaceWithBadReceiverHelper::test
 *                                 -Xcomp -XX:TieredStopAtLevel=1 TestInvokeinterfaceWithBadReceiver
 */

import jdk.internal.vm.annotation.Stable;

interface MyInterface {
    public String get();
}

// Single implementor
class MyClass implements MyInterface {
    @Stable
    String field = "42";

    public String get() {
        return field;
    }
}

public class TestInvokeinterfaceWithBadReceiver {

    public static void main(String[] args) {
        try {
            TestInvokeinterfaceWithBadReceiverHelper.test(new MyClass());
            throw new RuntimeException("No IncompatibleClassChangeError thrown!");
        } catch (IncompatibleClassChangeError e) {
            // Expected
        }
    }
}
