/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6853916
  @summary Window.setBackground() should not throw NPE
  @author anthony.petrov@sun.com: area=awt.toplevel
  @run main SetBackgroundNPE
*/

import java.awt.Window;

public class SetBackgroundNPE {
    public static void main(String args[]) {
        new Window(null).setBackground(null);
    }
}
