/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006140
 * @summary Javac NPE compiling Lambda expression on initialization expression of static field in interface
 * @compile LambdaInterfaceStaticField.java
 */

interface LambdaInterfaceStaticField {
  interface I {
     int m();
  }
  public static final I fld = () -> 5;
}
