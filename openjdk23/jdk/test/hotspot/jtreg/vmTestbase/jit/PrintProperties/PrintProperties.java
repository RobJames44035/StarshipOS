/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/PrintProperties.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.PrintProperties.PrintProperties
 */

package jit.PrintProperties;

import nsk.share.TestFailure;

public class PrintProperties {
  public static void main(String[] args) {
    System.out.println("System properties");
    System.getProperties().list(System.out);
  }
}
