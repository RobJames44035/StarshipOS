/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Utils;

import jdk.test.whitebox.WhiteBox;

/*
 * @test HandshakeTimeoutTest
 * @bug 8262454 8267651
 * @summary Test handshake timeout.
 * @requires vm.debug
 * @library /testlibrary /test/lib
 * @build HandshakeTimeoutTest
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver HandshakeTimeoutTest
 */

public class HandshakeTimeoutTest {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb =
            ProcessTools.createTestJavaProcessBuilder(
                    "-Xbootclasspath/a:.",
                    "-XX:+UnlockDiagnosticVMOptions",
                    "-XX:+WhiteBoxAPI",
                    "-XX:+HandshakeALot",
                    "-XX:GuaranteedSafepointInterval=10",
                    "-XX:ParallelGCThreads=1",
                    "-XX:ConcGCThreads=1",
                    "-XX:CICompilerCount=2",
                    "-XX:+UnlockExperimentalVMOptions",
                    "-XX:HandshakeTimeout=50",
                    "-XX:-CreateCoredumpOnCrash",
                    "HandshakeTimeoutTest$Test");

        OutputAnalyzer output = ProcessTools.executeProcess(pb);
        output.shouldNotHaveExitValue(0);
        output.reportDiagnosticSummary();
        // In rare cases the target wakes up and performs the handshake at the same time as we timeout.
        // Therefore it's not certain the timeout will find any thread.
        output.shouldMatch("has not cleared handshake op|No thread with an unfinished handshake op");
    }

    static class Test implements Runnable {
        public static void main(String[] args) throws Exception {
            Test test = new Test();
            Thread thread = new Thread(test);
            thread.start();
            thread.join();
        }

        @Override
        public void run() {
            while (true) {
                // If there is a safepoint this thread might still be able to perform
                // it's handshake in time. Therefore we loop util failure.
                WhiteBox.getWhiteBox().waitUnsafe(100);
            }
        }
    }
}
