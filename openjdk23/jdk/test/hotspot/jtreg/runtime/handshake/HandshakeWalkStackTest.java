/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test HandshakeWalkStackTest
 * @library /testlibrary /test/lib
 * @build HandshakeWalkStackTest
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI HandshakeWalkStackTest
 */

import jdk.test.lib.Asserts;
import jdk.test.whitebox.WhiteBox;

public class HandshakeWalkStackTest {

    public static void main(String... args) throws Exception {
        int iterations = 3;
        if (args.length > 0) {
            iterations = Integer.parseInt(args[0]);
        }
        test(iterations);
    }

    private static void test(int iterations) throws Exception {
        Thread loop_thread  = new Thread(() -> run_loop(create_list()));
        Thread alloc_thread = new Thread(() -> run_alloc());
        Thread wait_thread  = new Thread(() -> run_wait(new Object() {}));
        loop_thread.start();
        alloc_thread.start();
        wait_thread.start();

        WhiteBox wb = WhiteBox.getWhiteBox();
        int walked = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Iteration " + i);
            System.out.flush();
            Thread.sleep(200);
            walked = wb.handshakeWalkStack(loop_thread, false);
            Asserts.assertEQ(walked, 1, "Must have walked one thread stack");
            Thread.sleep(200);
            walked = wb.handshakeWalkStack(alloc_thread, false);
            Asserts.assertEQ(walked, 1, "Must have walked one thread stack");
            Thread.sleep(200);
            walked = wb.handshakeWalkStack(wait_thread, false);
            Asserts.assertEQ(walked, 1, "Must have walked one thread stack");
            Thread.sleep(200);
            walked = wb.handshakeWalkStack(Thread.currentThread(), false);
            Asserts.assertEQ(walked, 1, "Must have walked one thread stack");
        }
        Thread.sleep(200);
        walked = wb.handshakeWalkStack(null, true);
        Asserts.assertGT(walked, 4, "Must have walked more than three thread stacks");
    }

    static class List {
        List next;

        List(List next) {
            this.next = next;
        }
    }

    public static List create_list() {
        List head = new List(null);
        List elem = new List(head);
        List elem2 = new List(elem);
        List elem3 = new List(elem2);
        List elem4 = new List(elem3);
        head.next = elem4;

        return head;
    }

    public static void run_loop(List loop) {
        while (loop.next != null) {
            loop = loop.next;
        }
    }

    public static byte[] array;

    public static void run_alloc() {
        while (true) {
            // Write to public static to ensure the byte array escapes.
            array = new byte[4096];
        }
    }

    public static void run_wait(Object lock) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ie) {}
        }
    }
}
