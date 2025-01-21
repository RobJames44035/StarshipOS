/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.jdi.AttachingConnector.attachnosuspend;

import java.io.IOException;

public class attachnosuspend001t {
    public static void main(String args[]) {
        try {
            Thread.sleep(60000);
        } catch (Throwable e)
        {
            e.printStackTrace();
            System.exit(attachnosuspend001.JCK_STATUS_BASE + attachnosuspend001.FAILED);
        }
        System.exit(attachnosuspend001.JCK_STATUS_BASE + attachnosuspend001.PASSED);
    }
}
