/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026328
 * @requires vm.jvmti
 * @run main/othervm/native -agentlib:Test8026328 compiler.jsr292.cr8026328.Test8026328
 */

package compiler.jsr292.cr8026328;

public class Test8026328 {
  public static void main(String[] args) {
    Runnable r = () -> {};
    System.out.println(r.toString());
  }
}
