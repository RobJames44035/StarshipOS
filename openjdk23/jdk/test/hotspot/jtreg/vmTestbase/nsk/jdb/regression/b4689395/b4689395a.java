/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.regression.b4689395;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/* This is debuggee aplication */
public class b4689395a {
        static b4689395a _b4689395a = new b4689395a();
        final static String ERROR_MESSAGE  = "ERROR_M";

        public static void main(String args[]) {
                System.exit(Consts.JCK_STATUS_BASE + _b4689395a.runIt(args, System.out));
        }

        public int runIt(String args[], PrintStream out) {
                JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
                Log log = new Log(out, argumentHandler);

                minor();

                log.display("Debuggee PASSED");
                return Consts.TEST_PASSED;
        }

        public static void minor() {
                System.out.println("In the top of the method minor()."); // b4689395.LINE_NUMBER
                System.out.println("A breakpoint is here.");
                System.out.println("In the bottom of the method minor().");
                System.out.println(ERROR_MESSAGE);
        }
}
