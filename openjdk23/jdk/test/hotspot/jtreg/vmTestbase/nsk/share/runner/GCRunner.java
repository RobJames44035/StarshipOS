/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.runner;

/**
 *  Helper that calls GC.
 */
public class GCRunner implements Runnable {
        public void run() {
                System.gc();
        }
}
