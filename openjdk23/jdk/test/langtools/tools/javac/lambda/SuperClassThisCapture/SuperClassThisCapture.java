/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8336786
 * @summary VerifyError with lambda capture and enclosing instance references
 * @compile a/A.java SuperClassThisCapture.java
 * @run main SuperClassThisCapture
 */

public class SuperClassThisCapture extends a.A {

  public static void main(String[] args) {
    new SuperClassThisCapture().f(42);
    new SuperClassThisCapture().g();
  }

  public void f(int x) {
    Runnable r = () -> {
      System.err.println(x);
      new I();
    };
    r.run();
  }

  public void g() {
    Runnable r = () -> new I();
    r.run();
  }
}
