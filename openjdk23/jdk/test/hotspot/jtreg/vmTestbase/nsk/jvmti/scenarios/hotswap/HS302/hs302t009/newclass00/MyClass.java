/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS302.hs302t009r;

public class MyClass {

  private String name;

  public MyClass() {
    System.out.println(" Constructor..");
  }

  public void setName(String name) {
    this.name = name;
    this.name = "Throws Exception";

  }
   public String toString() {
    return name;
  }

  public boolean equals(Object obj) {
    return name.equals(obj.toString());
  }

}
