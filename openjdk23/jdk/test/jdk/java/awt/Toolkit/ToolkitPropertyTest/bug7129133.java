/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 7129133
 * @summary [macosx] Accelerators are displayed as Meta instead of the Command symbol
 * @author leonid.romanov@oracle.com
 * @library /test/lib
 * @build jdk.test.lib.Platform
 * @run main bug7129133
 */

import jdk.test.lib.Platform;

import java.awt.*;

public class bug7129133 {
    public static void main(String[] args) throws Exception {
        if (!Platform.isOSX()) {
            System.out.println("This test is for MacOS only. Automatically passed on other platforms.");
            return;
        }

        Toolkit.getDefaultToolkit();

        String cmdSymbol = "\u2318";
        String val = Toolkit.getProperty("AWT.meta", "Meta");

        if (!val.equals(cmdSymbol)) {
           throw new Exception("Wrong property value for AWT.meta. Expected: " + cmdSymbol + ", actual: " + val);
        }
    }
}
