/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test PutfieldError
 * @bug 8160551
 * @summary Throw ICCE rather than crashing for nonstatic final field in static initializer
 * @compile Bad.jasm
 * @run main PutfieldError
 */

public class PutfieldError {
  public static void main(java.lang.String[] unused) {
    try {
      Bad b = new Bad();
      System.out.println("Bad.i = " + 5);
      throw new RuntimeException("ICCE NOT thrown as expected");
    } catch (IncompatibleClassChangeError icce) {
      System.out.println("ICCE thrown as expected");
    }
  }
}
