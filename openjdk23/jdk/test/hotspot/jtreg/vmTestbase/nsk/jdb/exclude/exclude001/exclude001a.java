/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.exclude.exclude001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

import java.util.*;


/* This is debuggee aplication */
public class exclude001a {
    public static void main(String args[]) {
       exclude001a _exclude001a = new exclude001a();
       System.exit(exclude001.JCK_STATUS_BASE + _exclude001a.runIt(args, System.out));
    }

    static void lastBreak () {}

    static final String MYTHREAD  = "MyThread";
    static final int numThreads   = 3;   // number of threads.

    static JdbArgumentHandler argumentHandler;
    static Log log;

    static Object waitnotify = new Object();

    public int runIt(String args[], PrintStream out) {

        argumentHandler = new JdbArgumentHandler(args);
        log = new Log(out, argumentHandler);

        Thread holder [] = new Thread[numThreads];

        for (int i = 0; i < numThreads ; i++) {
            holder[i] = new MyThread(MYTHREAD + "-" + i);
            holder[i].start();
            try {
                holder[i].join();
            } catch (InterruptedException e) {
                log.complain("Main thread was interrupted while waiting for finish of " + MYTHREAD + "-" + i);
                return exclude001.FAILED;
            }
        }

        lastBreak();

        log.display("Debuggee PASSED");
        return exclude001.PASSED;
    }
}


class MyThread extends Thread {
    String name;

    public MyThread (String s) {
        super(s);
        name = s;
    }

    public void run() {

        exclude001a.lastBreak();

        long time = java.lang.System.currentTimeMillis();

        String caltype = GregorianCalendar.getInstance().getCalendarType();
    }
}
