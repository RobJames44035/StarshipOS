/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test HandshakeWalkExitTest
 * @summary This test tries to stress the handshakes with new and exiting threads
 * @library /testlibrary /test/lib
 * @build HandshakeWalkExitTest
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI HandshakeWalkExitTest
 */

import jdk.test.lib.Asserts;
import jdk.test.whitebox.WhiteBox;

public class HandshakeWalkExitTest  implements Runnable {

    @Override
    public void run() {
    }

    static volatile boolean exit_now = false;

    public static void main(String... args) throws Exception {
        int testRuns = 20;
        int testThreads = 128;

        HandshakeWalkExitTest test = new HandshakeWalkExitTest();

        Runnable hser = new Runnable(){
            public void run(){
                WhiteBox wb = WhiteBox.getWhiteBox();
                while(!exit_now) {
                    wb.handshakeWalkStack(null, true);
                }
            }
        };
        Thread hst = new Thread(hser);
        hst.start();
        for (int k = 0; k<testRuns ; k++) {
            Thread[] threads = new Thread[testThreads];
            for (int i = 0; i<threads.length ; i++) {
                threads[i] = new Thread(test);
                threads[i].start();
            }
        }
        exit_now = true;
        hst.join();
    }
}
