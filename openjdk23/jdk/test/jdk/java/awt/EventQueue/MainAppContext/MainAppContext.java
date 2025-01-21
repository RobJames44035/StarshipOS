/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8004584
 * @summary Tests 8004584
 * @author anthony.petrov@oracle.com, petr.pchelko@oracle.com
 * @modules java.desktop/sun.awt
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import sun.awt.*;

public class MainAppContext {

    public static void main(String[] args) {
        ThreadGroup secondGroup = new ThreadGroup("test");
        new Thread(secondGroup, () -> {
            SunToolkit.createNewAppContext();
            test(true);
        }).start();

        // Sleep on the main thread so that the AWT Toolkit is initialized
        // in a user AppContext first
        try { Thread.sleep(2000); } catch (Exception e) {}

        test(false);
    }

    private static void test(boolean expectAppContext) {
        boolean appContextIsCreated = AppContext.getAppContext() != null;
        if (expectAppContext != appContextIsCreated) {
            throw new RuntimeException("AppContext is created: " + appContextIsCreated
                                                 + " expected: " + expectAppContext);
        }
    }
}
