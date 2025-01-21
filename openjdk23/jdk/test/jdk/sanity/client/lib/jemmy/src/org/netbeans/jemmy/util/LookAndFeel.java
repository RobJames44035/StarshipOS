/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package org.netbeans.jemmy.util;

import javax.swing.UIManager;

/**
 * Class to provide look and feel related utility APIs
 */
public class LookAndFeel {

    /**
     * Checking whether the current look and feel is Metal L&F
     *
     * @return returns true if current look and feel is Metal L&F,
     *         otherwise false
     */
    public static boolean isMetal() {
        return isLookAndFeel("Metal");
    }

    /**
     * Checking whether the current look and feel is Nimbus L&F
     *
     * @return returns true if current look and feel is Nimbus L&F,
     *         otherwise false
     */
    public static boolean isNimbus() {
        return isLookAndFeel("Nimbus");
    }

    /**
     * Checking whether the current look and feel is Motif L&F
     *
     * @return returns true if current look and feel is Motif L&F,
     *         otherwise false
     */
    public static boolean isMotif() {
        return isLookAndFeel("Motif");
    }

    /**
     * Checking whether the current look and feel is GTK L&F
     *
     * @return returns true if current look and feel is GTK L&F,
     *         otherwise false
     */
    public static boolean isGTK() {
        return isLookAndFeel("GTK");
    }

    /**
     * Checking whether the current look and feel is Aqua L&F
     *
     * @return returns true if current look and feel is Aqua L&F,
     *         otherwise false
     */
    public static boolean isAqua() {
        return isLookAndFeel("Aqua");
    }

    /**
     * Checking whether the current look and feel is Windows L&F
     *
     * @return returns true if current look and feel is Windows L&F,
     *         otherwise false
     */
    public static boolean isWindows() {
        return UIManager.getLookAndFeel().getClass().
                getSimpleName().equals("WindowsLookAndFeel");
    }

    /**
     * Checking whether the current look and feel is WindowsClassic L&F
     *
     * @return returns true if current look and feel is WindowsClassic L&F,
     *         otherwise false
     */
    public static boolean isWindowsClassic() {
        return UIManager.getLookAndFeel().getClass().
                getSimpleName().equals("WindowsClassicLookAndFeel");
    }

    private static boolean isLookAndFeel(String id) {
        return UIManager.getLookAndFeel().getID().equals(id);
    }
}
