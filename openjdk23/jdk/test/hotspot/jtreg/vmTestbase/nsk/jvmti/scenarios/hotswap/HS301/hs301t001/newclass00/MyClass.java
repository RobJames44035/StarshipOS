/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS301.hs301t001;
public class MyClass {

  public MyClass() {
      System.out.println(" In side the MyClass () ");
  }

  public void doThis() {
      for(int i=0; i < 100; i++) {
          System.out.println(" In side the MyClass.doThis() ");
      }
  }

  public void DummyMethod() {
      System.out.println(" This DummyMethod ::  (is instance method.) .");
  }

}
