/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.internal.misc.Unsafe;

public class ClassLoaderNoUnnamedModule {
    static final Unsafe UNSAFE = Unsafe.getUnsafe();

    class TestClass extends ClassLoader {
        public boolean calledConstructor = false;

        public TestClass() {
            calledConstructor = true;
        }
    }

    static void testConstructorCall() throws Exception {
        // Use Unsafe allocateInstance to construct an instance of TestClass
        // which does not invoke its super's, java.lang.ClassLoader, constructor.
        // An unnamed module for this ClassLoader is not created.
        TestClass tc = (TestClass)UNSAFE.allocateInstance(TestClass.class);
        System.out.println("tc = " + tc + "tc's ctor called = " + tc.calledConstructor);
        Module unnamed_module = tc.getUnnamedModule();
        if (unnamed_module == null) {
            System.out.println("The unnamed module for this class loader is null");
        }

        tc.loadClass(String.class.getName());
        Class.forName(String.class.getName(), false, tc);
    }

    public static void main(String args[]) throws Exception {
        testConstructorCall();
    }
}
