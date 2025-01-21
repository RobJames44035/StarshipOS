/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.util.Locale;

import javax.accessibility.AccessibleBundle;

import static javax.accessibility.AccessibleRole.ALERT;
import static javax.accessibility.AccessibleRole.LABEL;
import static javax.accessibility.AccessibleRole.PANEL;
import static javax.accessibility.AccessibleState.MANAGES_DESCENDANTS;

/**
 * @test
 * @bug 8213516
 * @summary Checks basic functionality of AccessibleBundle class
 */
public final class Basic extends AccessibleBundle {

    private Basic(final String key) {
        this.key = key;
    }

    public static void main(final String[] args) {
        testStandardResource();
        testCustomResource();
    }

    private static void testCustomResource() {
        final Basic bundle = new Basic("managesDescendants");
        test(bundle.toDisplayString(Locale.ENGLISH), "manages descendants");
        test(bundle.toDisplayString("NonExistedBundle", Locale.ENGLISH),
             "managesDescendants");
    }

    private static void testStandardResource() {
        test(ALERT.toDisplayString(Locale.ENGLISH), "alert");
        test(ALERT.toDisplayString(Locale.JAPAN), "\u30a2\u30e9\u30fc\u30c8");
        test(LABEL.toDisplayString(Locale.ENGLISH), "label");
        test(LABEL.toDisplayString(Locale.JAPAN), "\u30e9\u30d9\u30eb");
        test(PANEL.toDisplayString(Locale.ENGLISH), "panel");
        test(PANEL.toDisplayString(Locale.JAPAN), "\u30D1\u30CD\u30EB");
        test(MANAGES_DESCENDANTS.toDisplayString(Locale.ENGLISH),
             "manages descendants");
        test(MANAGES_DESCENDANTS.toDisplayString(Locale.JAPAN),
             "\u5B50\u5B6B\u3092\u7BA1\u7406");
    }

    private static void test(final String actual, final String expected) {
        if (!actual.equals(expected)) {
            System.err.println("Expected: " + expected);
            System.err.println("Actual: " + actual);
            throw new RuntimeException("Wrong text");
        }
    }
}
