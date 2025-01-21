/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdb.unmonitor.unmonitor001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class unmonitor001a {
    static unmonitor001a _unmonitor001a = new unmonitor001a();

    public static void main(String args[]) {
        System.exit(unmonitor001.JCK_STATUS_BASE + _unmonitor001a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);
        int localInt = 0; // unmonitor001.BREAKPOINT_LINE
        localInt++;
        log.display("Debuggee PASSED");
        return unmonitor001.PASSED;
    }
}
