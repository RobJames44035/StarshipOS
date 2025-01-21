/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that List constructors do not throw unexpected
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessList
 */

public class HeadlessList {
    public static void main(String args[]) {
        List l;

        boolean exceptions = false;
        try {
            l = new List();
        } catch (java.awt.HeadlessException java_awt_HeadlessException) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            l = new List(10);
        } catch (java.awt.HeadlessException java_awt_HeadlessException) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            l = new List(1000);
        } catch (java.awt.HeadlessException java_awt_HeadlessException) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            l = new List(10, true);
        } catch (java.awt.HeadlessException java_awt_HeadlessException) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            l = new List(10, false);
        } catch (java.awt.HeadlessException java_awt_HeadlessException) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            l = new List(1000, true);
        } catch (java.awt.HeadlessException java_awt_HeadlessException) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");

        exceptions = false;
        try {
            l = new List(1000, false);
        } catch (java.awt.HeadlessException java_awt_HeadlessException) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("HeadlessException did not occur when expected");
    }
}
