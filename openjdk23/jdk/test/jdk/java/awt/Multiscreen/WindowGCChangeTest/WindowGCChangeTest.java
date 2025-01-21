/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4868278
  @summary Tests that GraphicsConfig for invisible (peerless) windows is
           updated after showing the window
  @library ../../regtesthelpers
  @build Util
  @run main WindowGCChangeTest
*/

import java.awt.*;
import java.awt.event.*;

import test.java.awt.regtesthelpers.Util;

public class WindowGCChangeTest {

    public static void main(final String[] args) {
        Robot robot = null;
        try
        {
            robot = new Robot();
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
            throw new RuntimeException("Test FAILED: couldn't create Robot instance", z);
        }
        Util.waitForIdle(robot);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        // check 2-screens systems only
        if (gds.length != 2)
        {
            return;
        }

        int defGDNo = 0;
        int nondefGDNo = 0;
        boolean isVirtualScreen = false;
        GraphicsDevice defgd = ge.getDefaultScreenDevice();
        for (int i = 0; i < gds.length; i++)
        {
            Rectangle r = gds[i].getDefaultConfiguration().getBounds();
            if ((r.x != 0) || (r.y != 0))
            {
                isVirtualScreen = true;
            }
            if (gds[i] == defgd)
            {
                defGDNo = i;
            }
            else
            {
                nondefGDNo = i;
            }
        }

        // doesn't test separate screens
        if (!isVirtualScreen)
        {
            return;
        }

        GraphicsDevice defGD = gds[defGDNo];
        GraphicsDevice nondefGD = gds[nondefGDNo];

        final GraphicsConfiguration defGC = defGD.getDefaultConfiguration();
        final GraphicsConfiguration nondefGC = nondefGD.getDefaultConfiguration();

        final Frame f = new Frame(defGC);
        f.setBounds(nondefGC.getBounds().x + 100, nondefGC.getBounds().y + 100, 100, 100);
        f.addWindowListener(new WindowAdapter()
        {
            public void windowActivated(WindowEvent ev)
            {
                GraphicsConfiguration gcf = f.getGraphicsConfiguration();
                if (gcf != nondefGC)
                {
                    throw new RuntimeException("Test FAILED: graphics config is not updated");
                }
                f.dispose();
            }
        });
        f.setVisible(true);
        Util.waitForIdle(robot);

        // paranoia - change def to nondef and vice versa
        final Frame g = new Frame(nondefGC);
        g.setBounds(defGC.getBounds().x + 100, defGC.getBounds().y + 100, 100, 100);
        g.addWindowListener(new WindowAdapter()
        {
            public void windowActivated(WindowEvent ev)
            {
                GraphicsConfiguration gcg = g.getGraphicsConfiguration();
                if (gcg != defGC)
                {
                    throw new RuntimeException("Test FAILED: graphics config is not updated");
                }
                g.dispose();
            }
        });
        g.setVisible(true);
        Util.waitForIdle(robot);

        // test fullscreen changes
        final Frame h = new Frame(defGC);
        h.setBounds(defGC.getBounds().x + 100, defGC.getBounds().y + 100, 100, 100);
        h.addWindowListener(new WindowAdapter()
        {
            public void windowActivated(WindowEvent ev)
            {
                GraphicsConfiguration gch = h.getGraphicsConfiguration();
                if (gch != nondefGC)
                {
                    throw new RuntimeException("Test FAILED: graphics config is not updated");
                }
                h.dispose();
            }
        });
        h.setUndecorated(true);
        nondefGD.setFullScreenWindow(h);
        Util.waitForIdle(robot);
    }
}
