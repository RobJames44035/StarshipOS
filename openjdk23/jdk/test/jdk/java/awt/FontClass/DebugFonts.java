/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4956241 8076979 8080953
 * @summary NPE debugging fonts
 * @run main/othervm DebugFonts
 */

import java.awt.Font;

public class DebugFonts {

   public static void main(String [] args) {
      System.setProperty("sun.java2d.debugfonts", "true");
      Font font = new Font("dialog", Font.PLAIN, 14);
      System.out.println(font);
      String s1 = font.getFamily();
      String s2 = font.getFontName();
   }
}
