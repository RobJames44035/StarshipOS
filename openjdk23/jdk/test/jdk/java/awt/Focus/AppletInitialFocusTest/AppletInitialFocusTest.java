/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4041703 4096228 4025223 4260929
  @summary Ensures that appletviewer sets a reasonable default focus for an Applet on start
  @library ../../regtesthelpers
  @build   Util
  @run main AppletInitialFocusTest
*/

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Robot;
import test.java.awt.regtesthelpers.Util;

public class AppletInitialFocusTest extends Frame {

    Robot robot = Util.createRobot();
    Button button = new Button("Button");

    public static void main(final String[] args) throws Exception {
        AppletInitialFocusTest app = new AppletInitialFocusTest();
        app.init();
        app.start();
    }

    public void init() {
        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        add(button);
        setVisible(true);
    }

    public void start() throws Exception {
        Thread thread = new Thread(new Runnable() {
                public void run() {
                    Util.waitTillShown(button);
                    robot.delay(1000); // delay the thread to let EDT to start dispatching focus events
                    Util.waitForIdle(robot);
                    if (!button.hasFocus()) {
                        throw new RuntimeException("Appletviewer doesn't set default focus correctly.");
                    }
                }
            });
        thread.start();
        thread.join();
    }
}
