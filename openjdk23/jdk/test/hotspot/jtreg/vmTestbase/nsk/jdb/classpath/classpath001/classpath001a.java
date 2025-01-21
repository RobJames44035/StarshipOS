/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.classpath.classpath001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class classpath001a {
    public static void main(String args[]) {
       classpath001a _classpath001a = new classpath001a();
       lastBreak();
       System.exit(classpath001.JCK_STATUS_BASE + _classpath001a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        lastBreak();

        log.display("Debuggee PASSED");
        return classpath001.PASSED;
    }
}
