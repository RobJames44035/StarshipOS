/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8192987
 * @summary keytool should remember real storetype if it is not provided
 * @library /test/lib
 * @build jdk.test.lib.SecurityTools
 *        jdk.test.lib.Utils
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 * @run main/othervm RealType
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RealType {

    public static void main(String[] args) throws Throwable {

        kt("-genkeypair -keyalg DSA -alias a -dname CN=A -keypass changeit -storetype jks")
                .shouldHaveExitValue(0);

        // -keypasswd command should be allowed on JKS
        kt("-keypasswd -alias a -new t0ps3cr3t")
                .shouldHaveExitValue(0);

        Files.delete(Paths.get("ks"));

        kt("-genkeypair -keyalg DSA -alias a -dname CN=A -keypass changeit -storetype pkcs12")
                .shouldHaveExitValue(0);

        // A pkcs12 keystore cannot be loaded as a JCEKS keystore
        kt("-list -storetype jceks").shouldHaveExitValue(1);
    }

    static OutputAnalyzer kt(String arg) throws Exception {
        return SecurityTools.keytool("-debug -keystore ks -storepass changeit " + arg);
    }
}
