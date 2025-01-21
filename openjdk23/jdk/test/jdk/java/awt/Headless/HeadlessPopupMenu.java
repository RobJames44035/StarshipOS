/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that PopupMenu constructors do not throw HeadlessException in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessPopupMenu
 */

public class HeadlessPopupMenu {
    public static void main(String args[]) {
            PopupMenu pm;
        boolean exceptions = false;
        try {
            pm = new PopupMenu();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            pm = new PopupMenu("Popup menu");
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");
    }
}
