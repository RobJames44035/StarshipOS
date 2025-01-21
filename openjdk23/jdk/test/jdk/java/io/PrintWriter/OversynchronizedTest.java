/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/* @test
   @bug 4905777
   @summary PrintWriter.println(Object) oversynchronized, can deadlock
*/

import java.io.PrintWriter;

public class OversynchronizedTest extends Thread {
    private static PrintWriter writer = new PrintWriter(System.out);
    private static TestObj testObj = new TestObj("This is a test.", writer);
    private static int loopNum = 100;

    public void run() {
        for(int i=0; i<loopNum; i++) {
            testObj.test();

            //passing an object to PrintWriter.println might cause deadlock
            //if the object has a synchronized toString() method.
            //using PrintWriter.println(testObj.toString()) won't have a problem
           writer.println(testObj);
        }
    }

    public static void main(String args[]) throws Exception {
        // should no NullPointerException
        writer.println((Object)null);

        int num = 5;

        OversynchronizedTest[] t = new OversynchronizedTest[num];
        for(int i=0; i<num; i++) {
            t[i] = new OversynchronizedTest();
            t[i].start();
        }

        for(int i=0; i <num; i++) {
            t[i].join();
        }

        System.out.println("Test completed");
    }
}

class TestObj {
    String mStr;

    TestObj(String str, PrintWriter writer) {
        mStr = str;
        this.writer = writer;
    }

    synchronized void test() {
        try {
            long t = Math.round(Math.random()*10);
            Thread.currentThread().sleep(t);
        } catch (InterruptedException e) {
            // jtreg timeout?
            // Only jtreg will interrupt this thread so it knows what to do:
            e.printStackTrace();
        }

        //the following line might cause hang if there is PrintWriter.println(testObj)
        //called by other threads.
        writer.println("In test().");
    }

    public synchronized String toString() {
        writer.println("Calling toString\n");
        return mStr;
    }

    private PrintWriter writer;
}
