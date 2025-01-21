/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdb.stop_at.stop_at003;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class stop_at003a {


    public static void main(String args[]) {
       stop_at003a _stop_at003a = new stop_at003a();
//       lastBreak();
       System.exit(stop_at003.JCK_STATUS_BASE + _stop_at003a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        stop_at003b b = new stop_at003b();
        b.foo();

        log.display("Debuggee PASSED");
        return stop_at003.PASSED;
    }
}

class stop_at003b {
    static int intField = 0;

    static { intField = 1; }      // stop_at003.LOCATIONS[0]

    { intField = 2; }             // stop_at003.LOCATIONS[1]

    stop_at003b () {
        intField++;               // stop_at003.LOCATIONS[2]
    }

    void foo () {
        try {
            throw new Exception("Exception in foo()");
        } catch (Exception e) {   // stop_at003.LOCATIONS[3]
            System.out.println("Exception in foo() is catched.");
        }
    }
}
