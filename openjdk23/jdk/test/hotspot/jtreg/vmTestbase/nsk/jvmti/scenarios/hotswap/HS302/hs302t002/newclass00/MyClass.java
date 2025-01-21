/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS302.hs302t002;
public class MyClass {

  private String name;

  public MyClass() {
    System.out.println(" Constructor..");
  }
  void setName(String name) {
    this.name = name;
    this.name="Default";
  }
  public String toString() {
    return name;
  }

  public boolean equals(Object obj) {
    return name.equals(obj.toString());
  }

}
