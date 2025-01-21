/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that Timer constructors do not throw unexpected exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessTimer
 */

public class HeadlessTimer {
    public static void main(String args[]) {
        Timer t = new Timer(100, null);
    }
}
