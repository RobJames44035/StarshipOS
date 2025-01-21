/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8013563 8028486
 * @summary Tests that windows are removed from windows list
 * @library /javax/swing/regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/sun.java2d
 * @build Util
 * @run main/othervm -Xms32M -Xmx32M WindowsLeak
*/

import java.awt.Frame;
import java.awt.Robot;
import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.Vector;

import sun.awt.AppContext;
import sun.java2d.Disposer;

public class WindowsLeak {

    private static volatile boolean disposerPhantomComplete;

    public static void main(String[] args) throws Exception {
        Robot r = new Robot();
        for (int i = 0; i < 100; i++) {
            Frame f = new Frame();
            f.pack();
            f.dispose();
        }
        r.waitForIdle();

        Disposer.addRecord(new Object(), () -> disposerPhantomComplete = true);

        while (!disposerPhantomComplete) {
            Util.generateOOME();
        }

        Vector<WeakReference<Window>> windowList =
                        (Vector<WeakReference<Window>>) AppContext.getAppContext().get(Window.class);

        if (windowList != null && !windowList.isEmpty()) {
            throw new RuntimeException("Test FAILED: Window list is not empty: " + windowList.size());
        }

        System.out.println("Test PASSED");
    }
}
