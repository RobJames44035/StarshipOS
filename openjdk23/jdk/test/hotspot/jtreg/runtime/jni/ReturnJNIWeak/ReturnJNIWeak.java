/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @bug 8166188
 * @requires vm.opt.ExplicitGCInvokesConcurrent != true
 * @summary Test return of JNI weak global refs from native calls.
 * @run main/othervm/native -Xint ReturnJNIWeak
 * @run main/othervm/native -Xcomp ReturnJNIWeak
 */

public final class ReturnJNIWeak {

    static {
        System.loadLibrary("ReturnJNIWeak");
    }

    private static final class TestObject {
        public final int value;

        public TestObject(int value) {
            this.value = value;
        }
    }

    private static volatile TestObject testObject = null;

    private static native void registerObject(Object o);
    private static native void unregisterObject();
    private static native Object getObject();

    // Create the test object and record it both strongly and weakly.
    private static void remember(int value) {
        TestObject o = new TestObject(value);
        registerObject(o);
        testObject = o;
    }

    // Remove both strong and weak references to the current test object.
    private static void forget() {
        unregisterObject();
        testObject = null;
    }

    // Verify the weakly recorded object
    private static void checkValue(int value) throws Exception {
        Object o = getObject();
        if (o == null) {
            throw new RuntimeException("Weak reference unexpectedly null");
        }
        TestObject t = (TestObject)o;
        if (t.value != value) {
            throw new RuntimeException("Incorrect value");
        }
    }

    // Verify we can create a weak reference and get it back.
    private static void testSanity() throws Exception {
        System.out.println("running testSanity");
        int value = 5;
        try {
            remember(value);
            checkValue(value);
        } finally {
            forget();
        }
    }

    // Verify weak ref value survives across collection if strong ref exists.
    private static void testSurvival() throws Exception {
        System.out.println("running testSurvival");
        int value = 10;
        try {
            remember(value);
            checkValue(value);
            System.gc();
            // Verify weak ref still has expected value.
            checkValue(value);
        } finally {
            forget();
        }
    }

    // Verify weak ref cleared if no strong ref exists.
    private static void testClear() throws Exception {
        System.out.println("running testClear");
        int value = 15;
        try {
          remember(value);
          checkValue(value);
          // Verify still good.
          checkValue(value);
          // Drop reference.
          testObject = null;
          System.gc();
          // Verify weak ref cleared as expected.
          Object recorded = getObject();
          if (recorded != null) {
            throw new RuntimeException("expected clear");
          }
        } finally {
          forget();
        }
    }

    // Verify passing a null value returns null and doesn't throw.
    private static void testNullValue() {
        System.out.println("running testNullValue");
        registerObject(null);
        if (getObject() != null) {
            throw new RuntimeException("expected null");
        }
    }

    public static void main(String[] args) throws Exception {
        testSanity();
        testSurvival();
        testClear();
        testNullValue();
    }
}
