/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package pkg;

import java.util.*;
import java.lang.*;
import java.io.*;

public interface TestInterface {

  /**
   * Test 11 passes.
   * @param p1 Test 12 passes.
   * @param p2 Test 13 passes.
   * @return a string.
   * @throws java.io.IOException Test 14 passes.
   * @throws java.lang.NullPointerException Test 15 passes.
   * @see java.lang.String
   * @see java.util.Vector
   */
  public String testInterface_method1(int p1, int p2) throws java.io.IOException,
java.lang.NullPointerException;

  /**
   * Test 16 passes.
   * @param p1 Test 17 passes.
   * @param p2 Test 18 passes.
   * @return a string.
   * @throws java.io.IOException Test 19 passes.
   * @throws java.lang.NullPointerException Test 20 passes.
   * @see java.lang.String
   * @see java.util.Vector
   */
  public String testInterface_method2(int p1, int p2) throws java.io.IOException,
java.lang.NullPointerException;

}
