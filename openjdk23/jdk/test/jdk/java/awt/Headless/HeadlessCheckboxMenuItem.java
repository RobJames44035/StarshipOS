/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that CheckboxMenuItem constructors throw HeadlessException
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessCheckboxMenuItem
 */

public class HeadlessCheckboxMenuItem {

    public static void main(String args[]) {
        CheckboxMenuItem c;

        boolean exceptions = false;
        try {
            c = new CheckboxMenuItem();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            c = new CheckboxMenuItem("Choices...");
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            c = new CheckboxMenuItem("Choices...", true);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            c = new CheckboxMenuItem("Choices...", false);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

    }
}
