/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package java.lang;

import java.lang.System.Logger;
import java.util.ResourceBundle;

/*
 * PatchedUsage is patched into java.base, it will be used by
 * PatchedClient to test when logger client is in patched module
 */
public class PatchedUsage {

    public static Logger getLogger(String name) {
        check();
        return System.getLogger(name);
    }

    public static Logger getLogger(String name, ResourceBundle rb) {
        check();
        return System.getLogger(name, rb);
    }

    private static void check() {
        final Module m = PatchedUsage.class.getModule();
        final ClassLoader moduleCL = m.getClassLoader();
        assertTrue(m.isNamed());
        assertTrue(moduleCL == null);
    }

    private static void assertTrue(boolean b) {
        if (!b) {
            throw new RuntimeException("expected true, but get false.");
        }
    }
}
