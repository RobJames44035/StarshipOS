/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test SuspendBlocked
 * @bug 8270085
 * @library /test/lib /testlibrary
 * @build SuspendBlocked
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI SuspendBlocked
 */

import jvmti.JVMTIUtils;

import jdk.test.lib.Asserts;
import jdk.test.whitebox.WhiteBox;

public class SuspendBlocked {

    public static void main(String... args) throws Exception {
        Thread suspend_thread = new Thread(() -> run_loop());
        suspend_thread.start();
        WhiteBox wb = WhiteBox.getWhiteBox();
        for (int i = 0; i < 100; i++) {
            try {
                JVMTIUtils.suspendThread(suspend_thread);
                wb.lockAndBlock(/* suspender= */ true);
                JVMTIUtils.resumeThread(suspend_thread);
                Thread.sleep(1);
            } catch (JVMTIUtils.JvmtiException e) {
                if (e.getCode() != JVMTIUtils.JVMTI_ERROR_THREAD_NOT_ALIVE) {
                    throw e;
                }
            }
        }
        suspend_thread.join();
    }

    public static void run_loop() {
        WhiteBox wb = WhiteBox.getWhiteBox();
        for (int i = 0; i < 100; i++) {
            wb.lockAndBlock(/* suspender= */ false);
        }
    }
}
