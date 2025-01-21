/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package pkg2;

import pkg1.*;

public class SubClass extends BaseClass {

  /*************************************************
   * This method should override the same public
   * method in the base class.
   *
   */
  public void publicMethod() {}


  /*************************************************
   * This method should not override the same package
   * private method in the base class because they
   * are in different packages.
   */
  public void packagePrivateMethod() {}

  /*************************************************
   * This method should not override anything because
   * the same method in the base class is private.
   *
   */
  public void privateMethod() {}

}
