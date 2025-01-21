/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
  @test
  @bug 4677084
  @summary Tests that the PageIncrement (BlockIncrement) and
           LineIncrement (UnitIncrement) cannot be < 1
  @key headful
*/

import java.awt.Scrollbar;

public class PageIncrementTest {
    static Scrollbar sb;

    public static void main(String[] args) {
        sb = new Scrollbar();
        sb.setBlockIncrement(0);
        sb.setUnitIncrement(0);

        if (sb.getBlockIncrement() < 1) {
            String msg = "Failed: getBlockIncrement() == " + sb.getBlockIncrement();
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
        if (sb.getUnitIncrement() < 1) {
            String msg = "Failed: getLineIncrement() == " + sb.getUnitIncrement();
            System.out.println(msg);
            throw new RuntimeException(msg);
        }

        sb.setBlockIncrement(-1);
        sb.setUnitIncrement(-1);

        if (sb.getBlockIncrement() < 1) {
            String msg = "Failed: getBlockIncrement() == " + sb.getBlockIncrement();
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
        if (sb.getUnitIncrement() < 1) {
            String msg = "Failed: getLineIncrement() == " + sb.getUnitIncrement();
            System.out.println(msg);
            throw new RuntimeException(msg);
        }

        sb.setBlockIncrement(2);
        sb.setUnitIncrement(2);

        if (sb.getBlockIncrement() != 2) {
            String msg = "Failed: getBlockIncrement() == " + sb.getBlockIncrement();
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
        if (sb.getUnitIncrement() != 2) {
            String msg = "Failed: getLineIncrement() == " + sb.getUnitIncrement();
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
        System.out.println("Test Pass!!");
    }
}
