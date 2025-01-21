/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.applet.Applet;
import java.awt.HeadlessException;

/*
 * @test
 * @summary Check HeadlessException occurrence when trying to create Applet
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessApplet
 */

public class HeadlessApplet {

    public static void main(String args[]) {
        boolean noExceptions = true;

        try {
            Applet a = new Applet();
        } catch (HeadlessException e) {
            noExceptions = false;
        }

        if (noExceptions) {
            throw new RuntimeException("No HeadlessException occured when creating Applet in headless mode");
        }
    }
}
