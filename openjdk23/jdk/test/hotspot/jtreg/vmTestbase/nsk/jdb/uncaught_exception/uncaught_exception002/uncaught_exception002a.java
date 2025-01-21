/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.uncaught_exception.uncaught_exception002;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class uncaught_exception002a {
    static uncaught_exception002a _uncaught_exception002a = new uncaught_exception002a();

    public static void main(String args[]) throws TenMultipleException {
       System.exit(uncaught_exception002.JCK_STATUS_BASE + _uncaught_exception002a.runIt(args, System.out));
    }

    public int runIt(String args[], PrintStream out) throws TenMultipleException {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        func(10);

        log.display("Debuggee PASSED");
        return uncaught_exception002.PASSED;
    }

    public static int func(int i) throws TenMultipleException {
        int localVar = 12345;

        if ( i % 10 == 0 ) {
            throw new TenMultipleException(i);
        }
        return i;
    }
}

class TenMultipleException extends Exception {
     TenMultipleException (int i) { super ("received " + i ); }
}
