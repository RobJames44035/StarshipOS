/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

/*
 * @test
 * @summary Check that MenuShortcut constructors do not throw unexpected
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessMenuShortcut
 */

public class HeadlessMenuShortcut {
    public static void main(String args[]) {
        MenuShortcut ms;
        ms = new MenuShortcut(KeyEvent.VK_A);
        ms = new MenuShortcut(KeyEvent.VK_A, true);
        ms = new MenuShortcut(KeyEvent.VK_A, false);
    }
}
