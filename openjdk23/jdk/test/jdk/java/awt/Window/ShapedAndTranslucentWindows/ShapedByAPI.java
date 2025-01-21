/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.awt.*;


/*
 * @test
 * @key headful
 * @summary Check if dynamically shaped window is moved and resized by
 *          API correctly.
 *
 * Test Description: Check if PERPIXEL_TRANSPARENT translucency type is
 *      supported on the current platform. Proceed if it is supported. Create
 *      a window and apply shape in componentResized listener. The shape should
 *      match the window size. Drag and resize the window using API and verify
 *      that shape is correctly applied both with pixels checking and clicks.
 *      Make the window appear on top of a known background. Repeat this for
 *      Window, Dialog, Frame.
 * Expected Result: If PERPIXEL_TRANSPARENT translucency type is supported, the
 *      window should appear with the expected shape. Clicks should come to visible
 *      parts of shaped window only and to background for clipped parts.
 *
 * @author mrkam
 * @author Dmitriy Ermashov (dmitriy.ermashov@oracle.com)
 * @library /lib/client
 * @run main ShapedByAPI
 */
public class ShapedByAPI extends Common {

    public static void main(String[] args) throws Exception {
        if (checkTranslucencyMode(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT))
            for (Class<Window> windowClass: WINDOWS_TO_TEST){
                new ShapedByAPI(windowClass).doTest();
            }
    }

    public ShapedByAPI(Class windowClass) throws Exception{
        super(windowClass);
    }

    @Override
    public void applyShape(){ applyDynamicShape(); }

    @Override
    public void doTest() throws Exception{
        super.doTest();

        checkDynamicShape();

        EventQueue.invokeAndWait(() -> {
            Point location = window.getLocationOnScreen();
            location.translate(random.nextInt(dl), random.nextInt(dl));
            window.setLocation(location);
        });
        robot.waitForIdle(delay);
        checkDynamicShape();

        EventQueue.invokeAndWait(() -> {
            Dimension size = window.getSize();
            window.setSize(size.width+random.nextInt(2*dl)-dl, size.height+random.nextInt(2*dl)-dl);
        });
        robot.waitForIdle(delay);
        checkDynamicShape();

        dispose();
    }
}
