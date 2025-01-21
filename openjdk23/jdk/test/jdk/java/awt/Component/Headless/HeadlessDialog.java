/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that Dialog constructors throw expected HeadlessException
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessDialog
 */

public class HeadlessDialog  {
    public static void main(String args[]) {
        boolean exceptions = false;
        Dialog d;

        try {
            d = new Dialog(new Frame("Hi there"));
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            d = new Dialog(new Frame("Hi there"), true);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            d = new Dialog(new Frame("Hi there"), false);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            d = new Dialog(new Frame("Hi there"), "Dialog title");
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            d = new Dialog(new Frame("Hi there"), "Dialog title", true);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            d = new Dialog(new Frame("Hi there"), "Dialog title", false);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

    }
}
