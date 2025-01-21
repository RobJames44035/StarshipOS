/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jdb.options.connect.connect001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class connect001a {
    static connect001a _connect001a = new connect001a();

    public static void main(String args[]) {
       System.exit(connect001.JCK_STATUS_BASE + _connect001a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        log.display("Debuggee PASSED");
        return connect001.PASSED;
    }
}
