/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that GrayFilter constructor and clone() method do not throw
 *          unexpected exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessGrayFilter
 */

public class HeadlessGrayFilter {
    public static void main(String args[]) {
         new GrayFilter(true, 60).clone();
    }
}
