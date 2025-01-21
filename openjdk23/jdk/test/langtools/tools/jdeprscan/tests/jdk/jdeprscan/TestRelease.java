/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8167965 8194308
 * @summary Test proper handling of the --release option.
 * @modules
 *      jdk.compiler/com.sun.tools.javac.jvm
 *      jdk.compiler/com.sun.tools.javac.platform
 *      jdk.jdeps/com.sun.tools.jdeprscan
 * @build jdk.jdeprscan.TestRelease
 * @run testng jdk.jdeprscan.TestRelease
 */

package jdk.jdeprscan;

import com.sun.tools.javac.platform.JDKPlatformProvider;
import com.sun.tools.jdeprscan.Main;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TestRelease {
    static boolean invoke(String arg) {
        System.err.println(">>> invoking Main.call with arguments: --list --release " + arg);
        boolean r = Main.call(System.out, System.err, "--list", "--release", arg);
        System.err.println(">>> Main.call returned " + r);
        return r;
    }

    @Test
    public void testSuccess() {
        for (String target : new JDKPlatformProvider().getSupportedPlatformNames()) {
            assertTrue(invoke(target));
        }
    }

    @Test
    public void testFailure() {
        assertFalse(invoke("5"));
        assertFalse(invoke("6"));
        assertFalse(invoke("7"));
    }
}
