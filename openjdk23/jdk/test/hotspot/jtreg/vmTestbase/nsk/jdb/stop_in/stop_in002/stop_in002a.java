/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.stop_in.stop_in002;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class stop_in002a {


    public static void main(String args[]) {
       stop_in002a _stop_in002a = new stop_in002a();
//       lastBreak();
       System.exit(stop_in002.JCK_STATUS_BASE + _stop_in002a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        stop_in002b b = new stop_in002b();
        stop_in002b.StaticNested.m1();
        b.inn.m2();
        b.foo(1);

        log.display("Debuggee PASSED");
        return stop_in002.PASSED;
    }
}

class stop_in002b {
    static int intField = 0;
    Inner inn = null;

    static { intField = 1; }

    stop_in002b () {
        intField++;
        inn = new Inner();
    }

    static class StaticNested {
        public static void m1() {}
    }

    class Inner {
        public void m2() {}
    }

    final int foo (int i) {return i++;}
}
