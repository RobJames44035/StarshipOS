/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

public class Hello {
  public static void main(String args[]) {
    System.out.println("Hello World");
    if (args.length > 0 && args[0].equals("testlambda")) {
        System.out.println(getRunnable());
    }
  }

  public static Runnable getRunnable() {
    return () -> {};
  }
}
