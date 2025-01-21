/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4177156
 * @key headful
 * @summary Tests that multiple level of window ownership doesn't cause
 * NullPointerException when showing a Window
 * @run main OwnedWindowShowTest
 */

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Window;

public class OwnedWindowShowTest {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(OwnedWindowShowTest::runTest);
    }

    static void runTest() {
        Frame parent = new Frame("OwnedWindowShowTest");
        try {
            Window owner = new Window(parent);
            Window window = new Window(owner);
            // Showing a window with multiple levels of ownership
            // should not throw NullPointerException
            window.setVisible(true);
        } finally {
            parent.dispose();
        }
    }
}
