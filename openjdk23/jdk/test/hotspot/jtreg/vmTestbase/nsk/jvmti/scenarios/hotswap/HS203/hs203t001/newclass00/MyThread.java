/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS203.hs203t001;
import java.util.concurrent.atomic.AtomicBoolean;
public class MyThread extends Thread {
        public static AtomicBoolean resume = new AtomicBoolean(false);
        public int threadState=10;
        public MyThread() {
                System.out.println(" Java ::..");
        }

        public void run() {
                resume.set(true);
                doThisFunction();
                doTask2();
        }
        public void doThisFunction() {
                System.out.println(" In side the doTask1");
                for(int i=0; i < 10; i++) {
                        threadState++;
                }
                return;
        }
        public void doTask2() {
                for (int i=0; i < 10; i++) {
                        threadState++;
                }
                return;
        }
}
