/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that DefaultKeyboardFocusManager constructor does not
 *          throw unexpected exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessDefaultKeyboardFocusManager
 */

public class HeadlessDefaultKeyboardFocusManager {
    public static void main(String args[]) {
        DefaultKeyboardFocusManager dfk = new DefaultKeyboardFocusManager();
    }
}
