/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS301.hs301t002;
public class MyClass {
  public int size=100;
  public static int count=0;

  public MyClass() {
      System.out.println(" In side the MyClass ().");
  }

  public void doThis() {
      for(int i=0; i < size;i++) {
          count++;
          DummyMethod();
      }
  }

  public void DummyMethod() {
      System.out.println(" Inside  DummyMethod (instance method ).");
  }

}
