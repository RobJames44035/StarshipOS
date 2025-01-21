/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.clear.clear002;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class clear002a {
    public static void main(String args[]) {
       clear002a _clear002a = new clear002a();
       lastBreak();
       System.exit(clear002.JCK_STATUS_BASE + _clear002a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        int i = func1(0) + A.func7(0);

        log.display("Debuggee PASSED");
        return clear002.PASSED;
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

    static class A {
        public static int func7 (int i) {
            return i++;
        }
    }
}
