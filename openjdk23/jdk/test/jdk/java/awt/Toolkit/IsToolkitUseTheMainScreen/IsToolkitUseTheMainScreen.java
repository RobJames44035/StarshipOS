/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.image.ColorModel;

/**
 * @test
 * @bug 8168307
 * @run main/othervm IsToolkitUseTheMainScreen
 * @run main/othervm -Djava.awt.headless=true IsToolkitUseTheMainScreen
 */
public final class IsToolkitUseTheMainScreen {

    public static void main(final String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            testHeadless();
        } else {
            testHeadful();
        }
    }

    private static void testHeadless() {
        try {
            Toolkit.getDefaultToolkit().getScreenSize();
            throw new RuntimeException("HeadlessException is not thrown");
        } catch (final HeadlessException ignored) {
            // expected exception
        }
        try {
            Toolkit.getDefaultToolkit().getColorModel();
            throw new RuntimeException("HeadlessException is not thrown");
        } catch (final HeadlessException ignored) {
            // expected exception
        }
    }

    private static void testHeadful() {
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc
                = ge.getDefaultScreenDevice().getDefaultConfiguration();
        Dimension gcSize = gc.getBounds().getSize();
        ColorModel gcCM = gc.getColorModel();

        Dimension toolkitSize = Toolkit.getDefaultToolkit().getScreenSize();
        ColorModel toolkitCM = Toolkit.getDefaultToolkit().getColorModel();

        if (!gcSize.equals(toolkitSize)) {
            System.err.println("Toolkit size = " + toolkitSize);
            System.err.println("GraphicsConfiguration size = " + gcSize);
            throw new RuntimeException("Incorrect size");
        }
        if (!gcCM.equals(toolkitCM)) {
            System.err.println("Toolkit color model = " + toolkitCM);
            System.err.println("GraphicsConfiguration color model = " + gcCM);
            throw new RuntimeException("Incorrect color model");
        }
    }
}
