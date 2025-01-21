/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.applet.Applet;
import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.BufferCapabilities.FlipContents;
import java.awt.Frame;
import java.awt.ImageCapabilities;
import java.util.HashSet;
import java.util.Set;

import sun.awt.AWTAccessor;
import sun.awt.AWTAccessor.ComponentAccessor;

import static java.awt.BufferCapabilities.FlipContents.BACKGROUND;
import static java.awt.BufferCapabilities.FlipContents.COPIED;
import static java.awt.BufferCapabilities.FlipContents.PRIOR;
import static java.awt.BufferCapabilities.FlipContents.UNDEFINED;

/**
 * @test
 * @key headful
 * @bug 8130390 8134732
 * @summary Applet fails to launch on virtual desktop
 * @modules java.desktop/sun.awt
 * @author Semyon Sadetsky
 */
public final class AppletFlipBuffer {

    static final ImageCapabilities[] ics = {new ImageCapabilities(true),
                                            new ImageCapabilities(false)};
    static final FlipContents[] cntx = {UNDEFINED, BACKGROUND, PRIOR, COPIED};
    static final Set<BufferCapabilities> bcs = new HashSet<>();

    static {
        for (final ImageCapabilities icFront : ics) {
            for (final ImageCapabilities icBack : ics) {
                for (final FlipContents cnt : cntx) {
                    bcs.add(new BufferCapabilities(icFront, icBack, cnt));
                }
            }
        }
    }

    public static void main(final String[] args) throws Exception {
        Applet applet = new Applet();
        Frame frame = new Frame();
        try {
            frame.setSize(10, 10);
            frame.add(applet);
            frame.setUndecorated(true);
            frame.setVisible(true);
            test(applet);
            System.out.println("ok");
        } finally {
            frame.dispose();
        }
    }

    private static void test(final Applet applet) {
        ComponentAccessor acc = AWTAccessor.getComponentAccessor();
        for (int i = 1; i < 10; ++i) {
            for (final BufferCapabilities caps : bcs) {
                try {
                    acc.createBufferStrategy(applet, i, caps);
                } catch (final AWTException ignored) {
                    // this kind of buffer strategy is not supported
                }
            }
        }
    }
}
