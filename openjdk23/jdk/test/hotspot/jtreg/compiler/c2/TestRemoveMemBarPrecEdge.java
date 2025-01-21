/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8287432
 * @summary Test removal of precedence edge of MemBarAcquire together with other now dead input nodes which visits a
 *          top node. This resulted in a crash before as it disconnected top from the graph which is unexpected.
 *
 * @run main/othervm -Xbatch compiler.c2.TestRemoveMemBarPrecEdge
 */
package compiler.c2;

public class TestRemoveMemBarPrecEdge {
    static boolean flag = false;

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            test();
            flag = !flag;
        }
    }

    public static void test() {
        // currentThread() is intrinsified and C2 emits a special AddP node with a base that is top.
        Thread t = Thread.currentThread();
        // getName() returns the volatile _name field. The method is inlined and we just emit a LoadN + DecodeN which
        // is a precedence edge input into both MemBarAcquire nodes below for the volatile field _name.
        if (flag) {
            t.getName();
        } else {
            t.getName();
        }
    }
}
