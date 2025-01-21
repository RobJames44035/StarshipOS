/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4250354
 * @key headful
 * @summary tests that JNI global refs are cleaned up correctly
 * @run main/timeout=600 NoEventsLeakTest
 */

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Frame;

public class NoEventsLeakTest extends Frame {
    static final int nLoopCount = 1000;

    private static void initialize() {
        NoEventsLeakTest app = new NoEventsLeakTest();
        boolean result = app.run();
        if (result) {
            throw new RuntimeException("Memory leak in Component");
        }
        System.out.println("Test Passed");
    }

    public boolean run() {
        setSize(10, 10);
        addNotify();
        for (int i = 0; i < nLoopCount; i++) {
            Canvas panel = new TestCanvas();
            add(panel, 0);
            remove(0);
            panel = null;
            System.gc();
        }
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
        }
        System.gc();
        System.out.println("Checking");
        return ((TestCanvas.created - TestCanvas.finalized) > 800);
    }

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(NoEventsLeakTest::initialize);
    }
}

class TestCanvas extends Canvas {
    static int finalized = 0;
    static int created = 0;
    static final int nLoopPrint = 100;

    public TestCanvas() {
        if (created % nLoopPrint == 0) {
            System.out.println("Created " + getClass() + " " + created);
        }
        created++;
    }

    @SuppressWarnings("removal")
    protected void finalize() {
        try {
            super.finalize();
            if (finalized % nLoopPrint == 0) {
                System.out.println("Finalized " + getClass() + " " + finalized);
            }
            finalized++;
        } catch (Throwable t) {
            System.out.println("Exception in " + getClass() + ": " + t);
        }
    }
}
