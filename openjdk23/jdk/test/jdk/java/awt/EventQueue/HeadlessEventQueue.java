/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that EventQueue constructor does not throw unexpected
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessEventQueue
 */

public class HeadlessEventQueue {
    public static void main(String args[]) {
        EventQueue eq = new EventQueue();
    }
}
