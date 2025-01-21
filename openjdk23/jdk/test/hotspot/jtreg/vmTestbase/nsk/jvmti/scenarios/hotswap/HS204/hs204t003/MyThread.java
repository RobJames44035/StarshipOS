/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS204.hs204t003;

public class MyThread extends Thread {


        private static volatile int intState=100;

        public static int count=100;

    static {
        System.out.println(" MyThread :: <cinit> static block.");
    }

        public MyThread() {
        intState++;
        System.out.println(" MyThread :: MyThread().");
        }

        public void run() {
                doMyWork();
        }

        public void doMyWork() {
                for(int i=0; i < count; i++) {
                        intState++;
                }
        }

        public int getIntState() {
                return intState;
        }
}
