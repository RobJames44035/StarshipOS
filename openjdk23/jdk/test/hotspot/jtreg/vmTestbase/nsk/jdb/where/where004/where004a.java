/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdb.where.where004;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class where004a {
    public static void main(String args[]) {
       where004a _where004a = new where004a();
       lastBreak();
       System.exit(where004.JCK_STATUS_BASE + _where004a.runIt(args, System.out)); // where004.FRAMES[6]
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        int i = func1(10); // where004.FRAMES[5]

        log.display("Debuggee PASSED");
        return where004.PASSED;
    }

    public int func1(int i) {
        return func2(i) + 1; // where004.FRAMES[4]
    }

    public int func2(int i) {
        return func3(i) + 1; // where004.FRAMES[3]
    }

    public int func3(int i) {
        return func4(i) + 1; // where004.FRAMES[2]
    }

    public int func4(int i) {
       return func5(i) + 1; // where004.FRAMES[1]
    }

    public int func5(int i) {
       return func6(i) + 1; // this is line for breakpoint // where004.FRAMES[0]
    }

    public int func6(int i) {
        return i-5;
    }
}
