/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Verifies the behaviour of Unsafe.allocateInstance
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main AllocateInstance
 */

import jdk.internal.misc.Unsafe;
import static jdk.test.lib.Asserts.*;

public class AllocateInstance {
    static final Unsafe UNSAFE = Unsafe.getUnsafe();

    class TestClass {
        public boolean calledConstructor = false;

        public TestClass() {
            calledConstructor = true;
        }
    }

    static void testConstructorCall() throws InstantiationException {
        // allocateInstance() should not result in a call to the constructor
        TestClass tc = (TestClass)UNSAFE.allocateInstance(TestClass.class);
        assertFalse(tc.calledConstructor);
    }

    abstract class AbstractClass {
        public AbstractClass() {}
    }

    static void testAbstractClass() {
        try {
            AbstractClass ac = (AbstractClass) UNSAFE.allocateInstance(AbstractClass.class);
            throw new AssertionError("Should throw InstantiationException for an abstract class");
        } catch (InstantiationException e) {
            // Expected
        }
    }

    interface AnInterface {}

    static void testInterface() {
        try {
            AnInterface ai = (AnInterface) UNSAFE.allocateInstance(AnInterface.class);
            throw new AssertionError("Should throw InstantiationException for an interface");
        } catch (InstantiationException e) {
            // Expected
        }
    }

    public static void main(String args[]) throws Exception {
        for (int i = 0; i < 20_000; i++) {
            testConstructorCall();
            testAbstractClass();
            testInterface();
        }
    }
}
