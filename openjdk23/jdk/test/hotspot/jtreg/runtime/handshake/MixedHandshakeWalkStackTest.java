/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test MixedHandshakeWalkStackTest
 * @library /testlibrary /test/lib
 * @build MixedHandshakeWalkStackTest
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI MixedHandshakeWalkStackTest
 */

import jdk.test.lib.Asserts;
import jdk.test.whitebox.WhiteBox;

public class MixedHandshakeWalkStackTest {
    public static Thread testThreads[];

    public static void main(String... args) throws Exception {
        testThreads = new Thread[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < testThreads.length; i++) {
            testThreads[i] = new Thread(() -> handshake());
        }

        for (Thread t : testThreads) {
            t.start();
        }

        handshake();

        for (Thread t : testThreads) {
            t.join();
        }
    }

    public static void handshake() {
        WhiteBox wb = WhiteBox.getWhiteBox();
        java.util.concurrent.ThreadLocalRandom rand = java.util.concurrent.ThreadLocalRandom.current();
        long end = System.currentTimeMillis() + 20000;
        while (end > System.currentTimeMillis()) {
            wb.asyncHandshakeWalkStack(testThreads[rand.nextInt(testThreads.length)]);
            wb.handshakeWalkStack(testThreads[rand.nextInt(testThreads.length)], false);
            wb.handshakeWalkStack(testThreads[rand.nextInt(testThreads.length)], true);
        }
    }
}
