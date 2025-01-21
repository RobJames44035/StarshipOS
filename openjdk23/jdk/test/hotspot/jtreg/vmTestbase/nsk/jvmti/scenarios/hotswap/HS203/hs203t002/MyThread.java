/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS203.hs203t002;
import java.util.concurrent.atomic.AtomicBoolean;
public class MyThread extends Thread {
        public static AtomicBoolean resume = new AtomicBoolean(false);
        public static AtomicBoolean resume2 = new AtomicBoolean(false);
        public int threadState=100;
        public MyThread() {
                System.out.println(" Java ::..");
        }
        public void run() {
                resume.set(true);
                doThisFunction();
                while(!resume.get());
                doTask3();
        }
        public void doThisFunction() {
                System.out.println(" In side the doTask1");
                for(int i=0; i < 100; i++) {
                        threadState++;
                }
                doTask2();
                resume2.set(true);
                return;
        }

        public void doTask2() {

                for (int i=0; i < 1000; i++) {
                        threadState++;
                }
                return;
        }
        public void doTask3() {
                        while(!resume.get());
                        for (int i=0; i < 10; i++) {
                        threadState++;
                        }
                        return;
        }
}
