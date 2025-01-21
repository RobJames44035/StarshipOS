/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.list.list002;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/* This is debuggee aplication */
public class list002a {
    static list002a _list002a = new list002a();

    public static void main(String args[]) { // list002.LINE_NUMBER
       System.exit(list002.JCK_STATUS_BASE + _list002a.runIt(args, System.out));
    }

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        log.display("Debuggee PASSED");
        return list002.PASSED;
    }
}
