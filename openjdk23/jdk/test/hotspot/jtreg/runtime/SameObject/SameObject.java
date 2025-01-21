/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.Asserts;

/*
 * @test
 * @bug 4784641
 * @summary -Xcheck:jni overly strict in JNI method IsSameObject
 *          Fixed in JDK1.3.1_10
 *          Fixed in JDK1.4.1_07
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run main/othervm/native -Xcheck:jni SameObject
 */
public class SameObject {

    public Object obj = new Object();

    static {
        System.loadLibrary("SameObject");
    }

    public native void createWeakRef(Object obj);

    public native int checkWeakRef();

    public static void main(String[] args) throws Exception {
        SameObject sameObject = new SameObject();

        int result = sameObject.test();
        Asserts.assertEquals(result, 0, "WeakRef still alive");
    }

    public int test() {
        createWeakRef(obj);
        obj = null;
        System.gc();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }

        return checkWeakRef();
    }
}
