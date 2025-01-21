/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.down.down002;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class down002a {
    public static void main(String args[]) {
       down002a _down002a = new down002a();
       lastBreak();
       System.exit(down002.JCK_STATUS_BASE + _down002a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        int i = func1(0);

        log.display("Debuggee PASSED");
        return down002.PASSED;
    }

    public int func1(int i) {
        return func2(i) + 1;
    }

    public int func2(int i) {
        return func3(i) + 1;
    }

    public int func3(int i) {
        return func4(i) + 1;
    }

    public int func4(int i) {
       return func5(i) + 1;
    }

    public int func5(int i) {
       return func6(i) + 1;
    }

    public int func6(int i) {
        return i-5;
    }
}
