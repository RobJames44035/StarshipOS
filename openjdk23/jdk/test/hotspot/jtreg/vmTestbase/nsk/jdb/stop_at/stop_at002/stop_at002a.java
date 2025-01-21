/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdb.stop_at.stop_at002;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class stop_at002a {
    public static void main(String args[]) {
       stop_at002a _stop_at002a = new stop_at002a();
       lastBreak();
       System.exit(stop_at002.JCK_STATUS_BASE + _stop_at002a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        new Nested(true).new DeeperNested().new DeepestNested().foo(false);
        Immersible var = new Inner().new MoreInner();
        var.foo("whatever");

        log.display("Debuggee PASSED");
        return stop_at002.PASSED;
    }

    class Nested {
        boolean flag;
        Nested (boolean b) {
            flag = b;
        }
        class DeeperNested {
            class  DeepestNested {
                public void foo(boolean input) {
                    flag = input; /* <--------  This is line number 64 */
                }
            }
        }
    }

    class Inner {
        class MoreInner implements Immersible {
            String content;

            public MoreInner() {
                content = "";
            }
            public void foo(String input) {
                content += input; /* <--------  This is line number 78 */
            }
        }
    }
}

interface Immersible {
    public void foo(String input);
}
