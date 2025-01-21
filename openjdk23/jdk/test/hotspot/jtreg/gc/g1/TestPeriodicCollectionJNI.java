/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package gc.g1;

/* @test
 * @bug 8218880
 * @summary Test that issuing a periodic collection while the GC locker is
 * held does not crash the VM.
 * @requires vm.gc.G1
 * @run main/othervm/native
 *    -Xbootclasspath/a:.
 *    -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *    -XX:+UseG1GC -XX:G1PeriodicGCInterval=100
 *    -XX:+G1PeriodicGCInvokesConcurrent
 *    -Xlog:gc*,gc+periodic=debug
 *    gc.g1.TestPeriodicCollectionJNI
 * @run main/othervm/native
 *    -Xbootclasspath/a:.
 *    -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *    -XX:+UseG1GC -XX:G1PeriodicGCInterval=100
 *    -XX:-G1PeriodicGCInvokesConcurrent
 *    -Xlog:gc*,gc+periodic=debug
 *    gc.g1.TestPeriodicCollectionJNI
 */

public class TestPeriodicCollectionJNI {
    static { System.loadLibrary("TestPeriodicCollectionJNI"); }

    private static native boolean blockInNative(byte[] array);
    private static native void unblock();

    public static void block() {
        if (!blockInNative(new byte[0])) {
            throw new RuntimeException("failed to acquire lock to dummy object");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long timeoutMillis = 2000;

        // Start thread doing JNI call
        BlockInNative blocker = new BlockInNative();
        blocker.start();

        try {
            // Wait for periodic GC timeout to trigger
            Thread.sleep(timeoutMillis);
        } finally {
            unblock();
        }
    }
}

class BlockInNative extends Thread {

    public void run() {
        TestPeriodicCollectionJNI.block();
    }

    native void unlock();
}
