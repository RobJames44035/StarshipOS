/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 7024749 8019990
 * @summary JDK7 b131---a crash in: Java_sun_awt_windows_ThemeReader_isGetThemeTransitionDurationDefined+0x75
 * @library ../../regtesthelpers
 * @build Util
 * @author Oleg Pekhovskiy: area=awt.toplevel
   @run main bug7024749
 */

import java.awt.*;
import test.java.awt.regtesthelpers.Util;

public class bug7024749 {
    public static void main(String[] args) {
        final Frame f = new Frame("F");
        f.setBounds(0,0,200,200);
        f.setEnabled(false); // <- disable the top-level
        f.setVisible(true);

        Window w = new Window(f);
        w.setBounds(300,300,300,300);
        w.add(new TextField(20));
        w.setVisible(true);

        Robot robot = Util.createRobot();
        robot.setAutoDelay(1000);
        Util.waitForIdle(robot);
        robot.delay(1000);
        Util.clickOnTitle(f, robot);
        Util.waitForIdle(robot);

        f.dispose();
        System.out.println("Test passed!");
    }
}
