/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS301.hs301t001;

public class MyClass {
  public int size=100;
  public int count=0;
  public MyClass() {
      System.out.println(" In side the MyClass () ");
  }

  public void doThis() {
      for(int i=0; i < size;i++) {
          count++;
      }
  }

  public static void DummyMethod() {
      System.out.println(" This DummyMethod ::  static method.");
  }

}
