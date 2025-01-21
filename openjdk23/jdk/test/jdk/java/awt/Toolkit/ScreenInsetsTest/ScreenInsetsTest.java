/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4737732
  @summary Tests that Toolkit.getScreenInsets() returns correct insets
  @author artem.ananiev: area=awt.toplevel
  @library ../../regtesthelpers
  @build Util
  @run main ScreenInsetsTest
*/

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import test.java.awt.regtesthelpers.Util;

public class ScreenInsetsTest
{
    public static void main(String[] args)
    {
        boolean passed = true;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        for (GraphicsDevice gd : gds) {
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            Rectangle gcBounds = gc.getBounds();
            Insets gcInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
            int left = gcInsets.left;
            int right = gcInsets.right;
            int bottom = gcInsets.bottom;
            int top = gcInsets.top;
            if (left < 0 || right < 0 || bottom < 0 || top < 0) {
                throw new RuntimeException("Negative value: " + gcInsets);
            }
            int maxW = gcBounds.width / 3;
            int maxH = gcBounds.height / 3;
            if (left > maxW || right > maxW || bottom > maxH || top > maxH) {
                throw new RuntimeException("Big value: " + gcInsets);
            }

            if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_BOTH))
            {
                // this state is used in the test - sorry
                continue;
            }

            Frame f = new Frame("Test", gc);
            f.setUndecorated(true);
            f.setBounds(gcBounds.x + 100, gcBounds.y + 100, 320, 240);
            f.setVisible(true);
            Util.waitForIdle(null);

            f.setExtendedState(Frame.MAXIMIZED_BOTH);
            Util.waitForIdle(null);

            Rectangle fBounds = f.getBounds();
            // workaround: on Windows maximized windows have negative coordinates
            if (fBounds.x < gcBounds.x)
            {
                fBounds.width -= (gcBounds.x - fBounds.x) * 2; // width is decreased
                fBounds.x = gcBounds.x;
            }
            if (fBounds.y < gcBounds.y)
            {
                fBounds.height -= (gcBounds.y - fBounds.y) * 2; // height is decreased
                fBounds.y = gcBounds.y;
            }
            Insets expected = new Insets(fBounds.y - gcBounds.y,
                                         fBounds.x - gcBounds.x,
                                         gcBounds.y + gcBounds.height - fBounds.y - fBounds.height,
                                         gcBounds.x + gcBounds.width - fBounds.x - fBounds.width);

            // On Windows 10 and up system allows undecorated maximized windows
            // to be placed over the taskbar so calculated insets might
            // be smaller than reported ones depending on the taskbar position
            if (gcInsets.top < expected.top
                    || gcInsets.bottom < expected.bottom
                    || gcInsets.left < expected.left
                    || gcInsets.right < expected.right)
            {
                passed = false;
                System.err.println("Wrong insets for GraphicsConfig: " + gc);
                System.err.println("\tExpected: " + expected);
                System.err.println("\tActual: " + gcInsets);
            }

            f.dispose();
        }

        if (!passed)
        {
            throw new RuntimeException("TEST FAILED: Toolkit.getScreenInsets() returns wrong value for some screens");
        }
    }
}
