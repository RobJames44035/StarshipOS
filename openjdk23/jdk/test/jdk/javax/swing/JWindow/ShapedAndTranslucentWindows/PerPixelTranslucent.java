/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @bug 8164811
 * @key headful
 * @summary Check if a per-pixel translucent window is dragged and resized
 *          by mouse correctly.
 * Test Description: Check if PERPIXEL_TRANSLUCENT translucency type is supported
 *      on the current platform. Proceed if they are supported. Create a window
 *      with some components in it, make window undecorated, apply translucent
 *      background of 0.5. Drag and resize the window using AWT Robot and verify
 *      that translucency is correctly applied with pixels checking. Make the
 *      window appear on top of a known background. Repeat this for JWindow,
 *      JDialog, JFrame.
 * Expected Result: If PERPIXEL_TRANSLUCENT translucency type is supported, the
 *      window should appear with the translucency. Only window background
 *      should be translucent, all the controls should be opaque.
 * @author mrkam
 * @library /lib/client
 * @build Common ExtendedRobot
 * @run main PerPixelTranslucent
 * @run main/othervm -Dsun.java2d.uiScale=1.5 PerPixelTranslucent
 */

public class PerPixelTranslucent extends Common {

    public static void main(String[] ignored) throws Exception {
        if (checkTranslucencyMode(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT))
            for (Class<Window> windowClass: WINDOWS_TO_TEST)
                new PerPixelTranslucent(windowClass).doTest();
    }

    public PerPixelTranslucent(Class windowClass) throws Exception {
        super(windowClass, 1.0f, 0.5f, false);
    }

    public void doTest() throws Exception {
        robot.waitForIdle(delay);
        checkTranslucent();
    }
}
