/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 7183458
 * @summary Verify advance of space is not overly widened by bold styling.
 * @run main StyledSpaceAdvance
 */
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Locale;

public class StyledSpaceAdvance {

    static String name = "Gulim";

    public static void main(String args[]) {
         for (int sz=9;sz<18;sz++) {
             test(sz);
         }
    }

    static void test(int sz) {
         Font reg = new Font(name, Font.PLAIN, sz);
         Font bold = new Font(name, Font.BOLD, sz);
         //System.out.println("reg="+reg);
         //System.out.println("bold="+bold);
         FontRenderContext frc = new FontRenderContext(null, false, false);
         if (reg.getFontName(Locale.ENGLISH).equals(name) &&
             bold.getFontName(Locale.ENGLISH).equals(name)) {
             Rectangle2D rb = reg.getStringBounds(" ", frc);
             Rectangle2D bb = bold.getStringBounds(" ", frc);
             if (bb.getWidth() > rb.getWidth() + 1.01f) {
                 System.err.println("reg="+reg+" bds = " + rb);
                 System.err.println("bold="+bold+" bds = " + bb);
                 throw new RuntimeException("Advance difference too great.");
             }
         } else {
             System.out.println("Skipping test because fonts aren't as expected");
         }
    }
}
