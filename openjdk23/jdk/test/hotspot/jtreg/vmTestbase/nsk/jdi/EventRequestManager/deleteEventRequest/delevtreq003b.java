/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.EventRequestManager.deleteEventRequest;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.io.*;

/**
 *  <code>delevtreq003b</code> is deugee's part of the delevtreq003.
 */

public class delevtreq003b {
    public final static int INITIAL_VALUE        = 0;
    public final static int BEFORE_REDEFINITION = 1;
    public final static int AFTER_REDEFINITION  = 2;

    public static boolean loadClass = false;
    public static int flag = INITIAL_VALUE;

    public static void runIt(boolean doWait) {

        flag = BEFORE_REDEFINITION;
//             ^^^^^^^^^^^^^^^^^^^ it will be redefined

    }
}
