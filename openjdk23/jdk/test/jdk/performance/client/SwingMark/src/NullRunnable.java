/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/** This is a simple class to be used as a "do nothing"
  * it is part of the timing system.
  *
  * @see SwingMarkPanel#runTests
  */

class NullRunnable implements Runnable {
   static NullRunnable singleton = new NullRunnable();
   public void run() {
   }
}
