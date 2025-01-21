/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that Scrollbar constructors throw HeadlessException in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessScrollbar
 */

public class HeadlessScrollbar {

    public static void main(String args[]) {
        Scrollbar s;
        boolean exceptions = false;

        try {
            s = new Scrollbar();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            s = new Scrollbar(Scrollbar.HORIZONTAL);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            s = new Scrollbar(Scrollbar.VERTICAL);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            s = new Scrollbar(Scrollbar.HORIZONTAL, 1, 10, 0, 100);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            s = new Scrollbar(Scrollbar.VERTICAL, 1, 10, 0, 100);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");
    }
}
