/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */


/*
  @test
  @bug 4173633
  @summary Test for infinite recursion when JMenu with separator
  @run main bug4173633
*/

import javax.swing.JMenu;

public class bug4173633 {
    public static void main(String[] args) {
        JMenu m = new JMenu("bug4173633");
        m.addSeparator();
        if (m.getItem(0) == m) {
            throw new RuntimeException("BUG 4173633 FAILED");
        }
    }
}
