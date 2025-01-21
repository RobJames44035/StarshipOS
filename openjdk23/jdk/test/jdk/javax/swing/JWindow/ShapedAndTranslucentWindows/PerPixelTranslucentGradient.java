/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @key headful
 * @bug 8032644 8164811
 * @summary Check if a per-pixel translucent window is dragged and resized by
 *          mouse correctly
 * Test Description: Check if PERPIXEL_TRANSLUCENT translucency type is supported
 *      on the current platform. Proceed if they are supported. Create a window
 *      with some components in it, make window undecorated, apply translucent
 *      background of 0 and have a gradient painted as background from
 *      fully-transparent to fully-opaque in componentResized listener. Drag and
 *      resize the window using AWT Robot and verify that translucency is
 *      correctly applied with pixels checking. Make the window appear on top of
 *      a known background. Repeat this for JWindow, JDialog, JFrame.
 * Expected Result: If PERPIXEL_TRANSLUCENT translucency type is supported,
 *      the window should appear as specified with the translucency. Only window
 *      background should be translucent, all the controls should be opaque.
 * @author mrkam
 * @library /lib/client
 * @build Common ExtendedRobot
 * @run main PerPixelTranslucentGradient
 * @run main/othervm -Dsun.java2d.uiScale=1.5 PerPixelTranslucentGradient
 */

public class PerPixelTranslucentGradient extends Common {

    public static void main(String[] ignored) throws Exception {
        if (checkTranslucencyMode(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT))
            for (Class<Window> windowClass: WINDOWS_TO_TEST)
                new PerPixelTranslucentGradient(windowClass).doTest();
    }

    public PerPixelTranslucentGradient(Class windowClass) throws Exception {
        super(windowClass, 1.0f, 0f, true);
    }

    public void doTest() throws Exception {
        robot.waitForIdle(delay);
        checkTranslucent();
    }

    @Override
    public void applyShape() {
        gradientWidth = window.getWidth();
        gradientHeight = window.getHeight();
    }
}
