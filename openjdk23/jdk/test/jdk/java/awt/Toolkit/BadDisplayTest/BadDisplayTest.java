/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @summary Test that Toolkit.getDefaultToolkit throws AWTError exception if bad DISPLAY variable was set
 * @bug 6818083
 *
 * @run shell/timeout=240 BadDisplayTest.sh
 */

import java.awt.AWTError;
import java.awt.Toolkit;

public class BadDisplayTest{
   public static void main(String[] args) {
       if (Boolean.getBoolean("java.awt.headless")) {
           return;
       }

       Throwable th = null;
        try {
            Toolkit.getDefaultToolkit();
        } catch (Throwable x) {
            th = x;
        }
        if ( !(th instanceof AWTError)) {
            System.exit(1);
        }
    }
}
