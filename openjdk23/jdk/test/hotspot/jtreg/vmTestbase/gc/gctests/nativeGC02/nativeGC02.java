/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase gc/gctests/nativeGC02.
 * VM Testbase keywords: [gc]
 * VM Testbase readme:
 * This test is a variation of nativeGC01.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm/native gc.gctests.nativeGC02.nativeGC02
 */

package gc.gctests.nativeGC02;

import nsk.share.test.*;
import nsk.share.gc.*;
import java.util.Vector;

public class nativeGC02 extends GCTestBase {
        private int count = 10000;
        CircularLinkedList cl;

        public native int nativeMethod02(CircularLinkedList cl);

        // This method is a callback that is invoked by a by the native
        // function "nativeMethod()" before the bowels of the nativeMethod()
        // function are executed.

        public void callbackGC() {
                cl = null;
                System.gc();
        }

        public void run() {
                int elementCount;

                cl = buildBigCircularLinkedList(); // build a 2 meg Circular Linked list.
                // Determine number of elements in the linked list with a native method
                // after GC has been done.
                try {
                        elementCount = nativeMethod02(cl);
                        if (elementCount == count) {
                                log.info("Test Passed");
                        } else {
                                log.info("Test Failed");
                                setFailed(true);
                        }
                } catch (Exception e) {
                        log.info(e.toString());
                        log.info("broken test");
                        setFailed(true);
                }
        }

        // build a circular linked list of 0.4 Meg
        private CircularLinkedList buildBigCircularLinkedList() {
                CircularLinkedList cl = new CircularLinkedList(100);
                for (int i = 0; i < count; i++)
                        cl.grow();
                return cl;
        }

        static {
                System.loadLibrary("nativeGC02");
        }

        public static void main(String args[]){
                Tests.runTest(new nativeGC02(), args);
        }
}
