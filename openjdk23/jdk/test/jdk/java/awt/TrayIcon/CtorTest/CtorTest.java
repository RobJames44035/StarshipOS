/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
  @test
  @bug 6759726
  @key headful
  @summary TrayIcon constructor throws NPE instead of documented IAE
  @author Dmitry Cherepanov area=awt.tray
  @run main CtorTest
*/

/**
 * CtorTest.java
 *
 * summary:  TrayIcon ctor throws IAE if image is null
 */

import java.awt.*;

public class CtorTest
{
    public static void main(String []s)
    {
        boolean isSupported = SystemTray.isSupported();
        if (isSupported) {
            try {
                TrayIcon tray = new TrayIcon(null);
            } catch(IllegalArgumentException e) {
                // ctor should throw IAE
            }
        }
    }
}
