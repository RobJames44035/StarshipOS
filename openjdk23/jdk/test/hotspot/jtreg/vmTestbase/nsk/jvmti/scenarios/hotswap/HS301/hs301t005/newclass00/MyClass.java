/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.scenarios.hotswap.HS301.hs301t005;

public class MyClass {

  private String name;

  public MyClass() {
    System.out.println(" Constructor..");
  }
  public void setName(String name) {
    this.name = name;
  }
  public String toString() {
    return "hs301t005";
  }

  public boolean equals(Object obj) {
    return name.equals(obj.toString());
  }

}
