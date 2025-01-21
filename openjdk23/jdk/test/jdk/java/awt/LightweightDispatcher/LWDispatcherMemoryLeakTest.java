/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import test.java.awt.regtesthelpers.Util;

/*
 @test
 @key headful
 @bug 7079254 8163261
 @summary Toolkit eventListener leaks memory
 @library ../regtesthelpers
 @build Util
 @compile LWDispatcherMemoryLeakTest.java
 @run main/othervm -Xmx10M LWDispatcherMemoryLeakTest
 */
public class LWDispatcherMemoryLeakTest {

    private static JFrame frame;
    private static WeakReference<JButton> button;
    private static WeakReference<JPanel> p;

    public static void init() throws Throwable {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame();
                frame.setLayout(new FlowLayout());
                button = new WeakReference<JButton>(new JButton("Text"));
                p = new WeakReference<JPanel>(new JPanel(new FlowLayout()));
                p.get().add(button.get());
                frame.add(p.get());

                frame.setBounds(500, 400, 200, 200);
                frame.setVisible(true);
            }
        });

        Util.waitTillShown(button.get());
        Util.clickOnComp(button.get(), new Robot());

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame.remove(p.get());
            }
        });

        Util.waitForIdle(null);
        assertGC();
    }

    public static void assertGC() throws Throwable {
        List<byte[]> alloc = new ArrayList<byte[]>();
        int size = 10 * 1024;
        while (true) {
            try {
                alloc.add(new byte[size]);
            } catch (OutOfMemoryError err) {
                break;
            }
        }
        alloc = null;
        String leakObjs = "";
        if (button.get() != null) {
            leakObjs = "JButton";
        }
        if (p.get() != null) {
            leakObjs += " JPanel";
        }
        if (leakObjs != "") {
            throw new Exception("Test failed: " + leakObjs + " not collected");
        }
    }

    public static void main(String args[]) throws Throwable {
        init();
    }
}
