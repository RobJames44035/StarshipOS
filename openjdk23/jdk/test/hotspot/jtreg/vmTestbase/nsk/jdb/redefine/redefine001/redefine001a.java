/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.redefine.redefine001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class redefine001a {
    static redefine001a _redefine001a = new redefine001a();

    public static void main(String args[]) {
       System.exit(redefine001.JCK_STATUS_BASE + _redefine001a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        RedefinedClass c = new RedefinedClass();

        flag = c.foo();

        lastBreak(); // at this point RedefinedClass is redefined.

        flag = c.foo();

        lastBreak();

        flag = c.foo();

        lastBreak();

        log.display("Debuggee PASSED");
        return redefine001.PASSED;
    }

    static String flag = "";
}
