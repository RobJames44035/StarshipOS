/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that ScrollPane constructors throw HeadlessException in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessScrollPane
 */

public class HeadlessScrollPane {
    public static void main(String args[]) {
        ScrollPane s;
        boolean exceptions = false;

        try {
            s = new ScrollPane();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            s = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            s = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            s = new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");
    }
}
