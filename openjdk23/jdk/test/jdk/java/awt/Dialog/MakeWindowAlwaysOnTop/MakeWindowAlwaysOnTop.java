/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6829546 8197808
  @summary tests that an always-on-top modal dialog doesn't make any windows always-on-top
  @author artem.ananiev: area=awt.modal
  @library ../../regtesthelpers
  @build Util
  @run main MakeWindowAlwaysOnTop
*/

import java.awt.Frame;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Point;
import java.awt.event.InputEvent;
import test.java.awt.regtesthelpers.Util;

public class MakeWindowAlwaysOnTop
{
    private static Frame f;
    private static Dialog d;

    // move away from cursor
    private final static int OFFSET_X = -20;
    private final static int OFFSET_Y = -20;

    public static void main(String[] args) throws Exception
    {
        Robot r = Util.createRobot();
        Util.waitForIdle(r);

        // Frame
        f = new Frame("Test frame");
        f.setBounds(100, 100, 400, 300);
        f.setBackground(Color.RED);
        f.setVisible(true);
        r.delay(100);
        Util.waitForIdle(r);

        // Dialog
        d = new Dialog(null, "Modal dialog", Dialog.ModalityType.APPLICATION_MODAL);
        d.setBounds(500, 500, 160, 160);
        d.setAlwaysOnTop(true);
        EventQueue.invokeLater(() ->  d.setVisible(true) );
        // Wait until the dialog is shown
        EventQueue.invokeAndWait(() -> { /* Empty */ });
        r.delay(100);
        Util.waitForIdle(r);

        // Click on the frame to trigger modality
        Point p = f.getLocationOnScreen();
        r.mouseMove(p.x + f.getWidth() / 2, p.y + f.getHeight() / 2);
        Util.waitForIdle(r);
        r.mousePress(InputEvent.BUTTON1_MASK);
        Util.waitForIdle(r);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        Util.waitForIdle(r);

        r.delay(100);
        Util.waitForIdle(r);

        // Dispose dialog
        d.dispose();
        r.delay(100);
        Util.waitForIdle(r);

        // Show another frame at the same location
        Frame t = new Frame("Check");
        t.setBounds(100, 100, 400, 300);
        t.setBackground(Color.BLUE);
        t.setVisible(true);
        r.delay(100);
        Util.waitForIdle(r);

        // Bring it above the first frame
        t.toFront();

        r.delay(200);
        Util.waitForIdle(r);


        Color c = r.getPixelColor(p.x + f.getWidth() / 2 - OFFSET_X, p.y + f.getHeight() / 2 - OFFSET_Y);
        System.out.println("Color = " + c);

        String exceptionMessage = null;
        // If the color is RED, then the first frame is now always-on-top
        if (Color.RED.equals(c)) {
            exceptionMessage = "Test FAILED: the frame is always-on-top";
        } else if (!Color.BLUE.equals(c)) {
            exceptionMessage = "Test FAILED: unknown window is on top of the frame";
        }

        // Dispose all the windows
        t.dispose();
        f.dispose();

        if (exceptionMessage != null) {
            throw new RuntimeException(exceptionMessage);
        } else {
            System.out.println("Test PASSED");
        }
    }
}
