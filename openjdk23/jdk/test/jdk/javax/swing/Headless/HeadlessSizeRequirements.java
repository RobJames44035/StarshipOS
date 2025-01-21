/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that SizeRequirements constructors do not throw unexpected exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessSizeRequirements
 */

public class HeadlessSizeRequirements {
    public static void main(String args[]) {
        SizeRequirements sr;
        sr = new SizeRequirements();
        sr = new SizeRequirements(20, 20, 20, (float) 1.0);
    }
}
