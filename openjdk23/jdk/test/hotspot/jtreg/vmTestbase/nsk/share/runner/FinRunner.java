/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


package nsk.share.runner;

/**
 *  Helper that calls System.runFinalization().
 */
public class FinRunner implements Runnable {
        public void run() {
                System.runFinalization();
        }
}
