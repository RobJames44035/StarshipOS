/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.use.use001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class use001a {
    static use001a _use001a = new use001a();

    public static void main(String args[]) {
       System.exit(use001.JCK_STATUS_BASE + _use001a.runIt(args, System.out));
    }

//    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

//        lastBreak();

        log.display("Debuggee PASSED");
        return use001.PASSED;
    }
}
