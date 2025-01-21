/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS302.hs302t007r;

public class MyClass {

  private String name;

  public MyClass() {
    System.out.println(" Constructor..");
  }
  synchronized public void setName(String name) {
    this.name = name;
    this.name="synchronized";
  }
   public String toString() {
    return name;
  }

  public boolean equals(Object obj) {
    return name.equals(obj.toString());
  }

}
