/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package pkg1;

public class BaseClass {

  /*************************************************
   * A public method that can be overridden.
   *
   */
  public void publicMethod() {}


  /*************************************************
   * A package private method that can only
   * be overridden by sub classes in the same package.
   *
   */
  void packagePrivateMethod() {}

  /*************************************************
   * A private that cannot be overridden.
   *
   */
  private void privateMethod() {}

  /**
   * These comments will be copied to the overridden method.
   */
  public void overriddenMethodWithDocsToCopy() {}

  /**
   * @deprecated func1 deprecated
   */
  @Deprecated
  public void func1() {}

  /**
   * @deprecated func2 deprecated
   */
  @Deprecated
  public void func2() {}

  /**
   * @deprecated func3 deprecated
   */
  @Deprecated
  public void func3() {}

}
