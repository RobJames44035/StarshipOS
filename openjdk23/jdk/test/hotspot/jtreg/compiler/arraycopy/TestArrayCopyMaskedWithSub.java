/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8305524
 * @run main/othervm -Xbatch compiler.arraycopy.TestArrayCopyMaskedWithSub
 */

package compiler.arraycopy;

public class TestArrayCopyMaskedWithSub {
    private static char[] src = {'A', 'A', 'A', 'A', 'A'};
    private static char[] dst = {'B', 'B', 'B', 'B', 'B'};

    private static void copy(int nlen) {
      System.arraycopy(src, 0, dst, 0, -nlen);
    }

    public static void main(String[] args) {
      for (int i = 0; i < 25000; i++) {
        copy(0);
      }
      copy(-5);
      for (char c : dst) {
        if (c != 'A') {
          throw new RuntimeException("Wrong value!");
        }
      }
      System.out.println("PASS");
    }
}
