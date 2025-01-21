/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;
import jdk.test.lib.apps.LingeredApp;

public class LingeredAppWithLockInVM extends LingeredApp {

    private static class LockerThread implements Runnable {
        public void run() {
            while (!isReady()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            WhiteBox wb = WhiteBox.getWhiteBox();
            wb.lockAndStuckInSafepoint();
        }
    }


    public static void main(String args[]) {
        if (args.length != 1) {
            System.err.println("Lock file name is not specified");
            System.exit(7);
        }

        Thread t = new Thread(new LockerThread());
        t.setName("LockerThread");
        t.start();

        LingeredApp.main(args);
    }
 }
