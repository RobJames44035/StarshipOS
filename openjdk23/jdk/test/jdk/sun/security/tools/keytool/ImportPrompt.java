/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.File;
import java.security.KeyStore;

/**
 * @test
 * @bug 8172975
 * @summary SecurityTools.keytool() needs to accept user input
 * @library /test/lib
 * @build jdk.test.lib.SecurityTools
 *        jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 * @run main ImportPrompt
 */

public class ImportPrompt {

    private static final String COMMON =
            "-storetype jks -storepass changeit -keypass changeit -debug";

    public static void main(String[] args) throws Throwable {

        kt("-keystore ks1 -genkeypair -keyalg DSA -alias a -dname CN=A");
        kt("-keystore ks1 -exportcert -alias a -file a.cert");

        // Just create a keystore
        kt("-keystore ks2 -genkeypair -keyalg DSA -alias b -dname CN=B");

        // no response text, assume no
        kt("-keystore ks2 -importcert -alias a -file a.cert");
        Asserts.assertFalse(hasA());

        // no reply is no
        SecurityTools.setResponse("no");
        kt("-keystore ks2 -importcert -alias a -file a.cert");
        Asserts.assertFalse(hasA());

        // explicit yes
        SecurityTools.setResponse("yes");
        kt("-keystore ks2 -importcert -alias a -file a.cert");
        Asserts.assertTrue(hasA());

        // remove it
        kt("-keystore ks2 -delete -alias a");
        Asserts.assertFalse(hasA());

        // the previous "yes" will not be remembered
        kt("-keystore ks2 -importcert -alias a -file a.cert");
        Asserts.assertFalse(hasA());

        // add with -noprompt
        SecurityTools.setResponse("");
        kt("-keystore ks2 -importcert -alias a -file a.cert -noprompt");
        Asserts.assertTrue(hasA());
    }

    private static OutputAnalyzer kt(String cmd) throws Throwable {
        return SecurityTools.keytool(COMMON + " " + cmd)
                .shouldHaveExitValue(0);
    }

    private static boolean hasA() throws Exception {
        return KeyStore.getInstance(new File("ks2"), "changeit".toCharArray())
                .containsAlias("a");
    }
}
