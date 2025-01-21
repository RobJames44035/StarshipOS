/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package nsk.jdb.list.list003;

import nsk.share.Log;
import nsk.share.jdb.JdbArgumentHandler;

import java.io.PrintStream;


/* This is debuggee application */
public class list003a {
    static list003a _list003a = new list003a();

    public static void main(String[] args) {
       System.exit(list003.JCK_STATUS_BASE + _list003a.runIt(args, System.out));
    }

    public int runIt(String[] args, PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        log.display("Debuggee PASSED");
        return list003.PASSED;
    }
}
