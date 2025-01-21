/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS202.hs202t002;
public class MyThread extends Thread {
    private int val = 100;

    public void run() {
        playWithThis();
    }

    public void playWithThis() {
        try {
            display();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private synchronized void display() throws Exception {
        // increment val by 10 in the redefined version
        // of display(). The original version only incremented by 1.
        val += 10;
        throw new Exception(" Dummy Exception...");
    }

    public int getValue() {
        return val;
    }
}
