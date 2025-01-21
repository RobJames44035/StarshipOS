/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 6668439
 * @summary Verifies that no exceptions are thrown when frame is resized to 0x0
 * @author Dmitri.Trembovetski@sun.com: area=Graphics
 * @run main/othervm IAEforEmptyFrameTest
 * @run main/othervm -Dsun.java2d.d3d=false IAEforEmptyFrameTest
 */

import javax.swing.JFrame;

public class IAEforEmptyFrameTest {
    public static void main(String[] args) {
        JFrame f = null;
        try {
            f = new JFrame("IAEforEmptyFrameTest");
            f.setUndecorated(true);
            f.setBounds(100, 100, 320, 240);
            f.setVisible(true);
            try { Thread.sleep(1000); } catch (Exception z) {}
            f.setBounds(0, 0, 0, 0);
            try { Thread.sleep(1000); } catch (Exception z) {}
            f.dispose();
        } finally {
            f.dispose();
        };
    }
}
