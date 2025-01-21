/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase gc/gctests/FinalizerGC01.
 * VM Testbase keywords: [gc]
 * VM Testbase readme:
 * This rather contrived test checks the interplay of garbage collection
 * and finalizers. Just before a 0.5 Meg linked list is garbage collected,
 * the finalizer generates more garbage . The test fails if an OutOfMemoryError
 * is thrown.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm gc.gctests.FinalizerGC01.FinalizerGC01
 */

package gc.gctests.FinalizerGC01;

import nsk.share.TestFailure;

class node {
        byte [] arr;
        node next;
        node prev;
        node(int info){ arr = new byte[128]; }
}

class CircularLinkedList{
        private void addElement(int info) {
                node newnode;
                newnode = new node(info);
                if (Root == null){
                        Root = newnode;
                        Root.next = Root;
                        Root.prev = Root;
                } else{
                        newnode.next = Root.next;
                        Root.next.prev = newnode;
                        Root.next = newnode;
                        newnode.prev = Root;
                }
        }
        int elementCount() {
                node p;
                int count;
                p = Root;
                count = 0;
                do {
                        p = p.prev;
                        count++;
                }while(p != Root);
                return count;
        }
        public void build1MegList() {
                for (int i = 0 ; i < NELEMENTS ; i++)
                        addElement(i);
        }
        node Root=null;
        static final int NELEMENTS=4096;
}
class CircularLinkedListFinal extends CircularLinkedList {
        protected void finalize () {
                CircularLinkedList cl = null;
                // generate 1.5Meg more garbage
                int i = 0;
                int gcFails=0;
                while (i < 3){
                        Root = null;
                        gcFails=0;
                        while (gcFails < NGCFAILS) {
                                try {
                                        cl = new CircularLinkedList();
                                        cl.build1MegList();
                                        cl = null;
                                        break;
                                }
                                catch (OutOfMemoryError e) {
                                        System.gc();
                                        gcFails++;
                                }
                        }
                        if (gcFails >= NGCFAILS) break;
                        i++;
                }
                if (i < 3) throw new OutOfMemoryError();
        }
        private static final int NGCFAILS=10;
}

public class FinalizerGC01 {
        public static void main(String args[]) {
                int count = 0;
                int gcFails = 0;
                CircularLinkedListFinal cl = null;
                while (count < 64) {
                        gcFails = 0;
                        while (gcFails<NGCFAILS) {
                                try {
                                        cl = new CircularLinkedListFinal();
                                        cl.build1MegList();
                                        doComputation();
                                        cl = null;
                                        break;
                                } catch (OutOfMemoryError e) {
                                        gcFails++;
                                        System.gc();
                                }
                        }
                        if (gcFails >= NGCFAILS) break;
                        count++;
                }
                if (count < 64)
                        throw new TestFailure("Test failed on " + count + " iteration");
                else
                        System.out.println("Test Passed");
        }
        static void doComputation(){
                long i = 0;
                while ( i < 1000000L) { i++; }
        }
        private static final int NGCFAILS=10;
}
