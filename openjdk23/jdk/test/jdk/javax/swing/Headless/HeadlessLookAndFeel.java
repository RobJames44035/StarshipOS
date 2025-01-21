/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.plaf.metal.MetalLookAndFeel;

/*
 * @test
 * @summary Check that MetalLookAndFeel constructor does not throw unexpected
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessLookAndFeel
 */

public class HeadlessLookAndFeel {
    public static void main(String args[]) {
        new MetalLookAndFeel();
    }
}
