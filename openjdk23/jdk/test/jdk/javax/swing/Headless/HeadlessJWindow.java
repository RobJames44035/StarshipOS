/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;
import java.awt.*;

/*
 * @test
 * @summary Check that JWindow constructors throw HeadlessException in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessJWindow
 */

public class HeadlessJWindow {
    public static void main(String args[]) {
        boolean exceptions = false;
        JWindow w;

        try {
            w = new JWindow();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            w = new JWindow(new Frame("Frame title"));
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

    }
}
