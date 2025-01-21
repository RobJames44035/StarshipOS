/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check whether Button constructors throw HeadlessException in
 *          headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessButton
 */

public class HeadlessButton {

    public static void main(String args[]) {
        Button b;

        boolean exceptions = false;
        try {
            b = new Button();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");


        exceptions = false;
        try {
            b = new Button("Press me");
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");
    }
}
