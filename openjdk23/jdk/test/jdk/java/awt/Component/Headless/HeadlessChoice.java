/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that Choice constructor throws HeadlessException in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessChoice
 */

public class HeadlessChoice {

    public static void main(String args[]) {
        boolean exceptions = false;
        try {
            Choice c = new Choice();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");
    }
}
