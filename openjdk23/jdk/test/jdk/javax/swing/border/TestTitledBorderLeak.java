/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import javax.swing.border.TitledBorder;

/**
 * @test
 * @bug 8204963
 * @summary Verifies TitledBorder's memory leak
 * @library /javax/swing/regtesthelpers
 * @build Util
 * @run main/timeout=60/othervm -Xmx32m TestTitledBorderLeak
 */
public final class TestTitledBorderLeak {

    public static void main(String[] args) throws Exception {
        Reference<TitledBorder> border = getTitleBorder();
        int attempt = 0;
        while (border.get() != null) {
            Util.generateOOME();
            System.out.println("Not freed, attempt: " + attempt++);
        }
    }

    private static Reference<TitledBorder> getTitleBorder() {
        TitledBorder tb = new TitledBorder("");
        return new WeakReference<>(tb);
    }
}
