/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS204.hs204t002;
public class MyThread extends Thread{
        public static int value=100;
        static {
                System.out.println("  ... Break Point here..");
                System.out.println("  ... Break Point here..");
                System.out.println("  ... Break Point here..");
                runToBreak();
        }
        public static void runToBreak() {
                System.out.println(" .. Do break here..");
        }
        private int state =0;
        public MyThread() {
                System.out.println(" ... In Constructor...");
        }
        public int getStateValue() {
                return state;
        }
        public void run() {
                for(int i=0; i < value; i++) {
                        state++;
                        getStateValue();
                }
        }
}
