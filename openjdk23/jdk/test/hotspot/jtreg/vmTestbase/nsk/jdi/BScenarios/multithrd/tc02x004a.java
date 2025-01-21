/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.multithrd;

import nsk.share.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc02x004a</code> is deugee's part of the tc02x004.
 */
public class tc02x004a {

    public final static int threadCount = 3;
    static Log log;

    public final static int checkClassBrkpLine = 79;
    Thread[] thrds = new Thread[threadCount];

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);

        tc02x004a obj = new tc02x004a();

        try {
            for (int i = 0; i < obj.thrds.length; i++ ) {
                obj.thrds[i].join();
            }
        } catch (InterruptedException e) {
        }
        log.display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    tc02x004a() {
        for (int i = 0; i < thrds.length; i++ ) {
            thrds[i] = JDIThreadFactory.newThread(new Thready("Thread-" + (i+1)));
            thrds[i].start();
        }
    }

    static class Thready extends NamedTask {
        Thready(String name) {
            super(name);
        }

        public void run() {
            log.display(getName() + ":: creating tc02x004aClass1");
            new tc02x004aClass1(getName());
        }
    }
}


class tc02x004aClass1 {
    public tc02x004aClass1(String thrdName) { // checkClassBrkpLine
        tc02x004a.log.display("tc02x004aClass1::constructor is called from"
                                    + thrdName);
    }
}
