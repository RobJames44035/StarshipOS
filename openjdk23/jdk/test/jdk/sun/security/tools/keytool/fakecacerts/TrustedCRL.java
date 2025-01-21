/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8244148
 * @summary Test keytool -printcrl with -keystore and -trustcacerts options
 * @library /test/lib
 * @library /test/jdk/sun/security/util/module_patch
 * @build java.base/sun.security.util.FilePaths
 * @modules java.base/sun.security.util
 *          java.base/jdk.internal.misc
 * @run main TrustedCRL
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.security.KeyStoreUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TrustedCRL {

    // The --patch-module must be explicitly specified on the keytool
    // command line because it's in a separate process
    private static final String PATCH_OPTION;

    static {
        String tmp = "";
        for (String a : jdk.internal.misc.VM.getRuntimeArguments()) {
            if (a.startsWith("--patch-module")) {
                tmp = "-J" + a + " ";
                break;
            }
        }
        PATCH_OPTION = tmp;
    }

    static OutputAnalyzer kt(String cmd) throws Exception {
        return SecurityTools.keytool(cmd + " -keystore ks"
                + " -storepass changeit")
                .shouldHaveExitValue(0);
    }

    static OutputAnalyzer patchcmd(String cmd, String options)
            throws Exception {
        return kt(PATCH_OPTION + " -" + cmd + " " + options);
    }

    static void rm(String s) throws IOException {
        System.out.println("---------------------------------------------");
        System.out.println("$ rm " + s);
        Files.deleteIfExists(Paths.get(s));
    }

    public static void main(String[] args) throws Exception {

        // Test -printcrl with root CA in cacerts keystore
        kt("-genkeypair -alias myca -dname CN=CA -keyalg RSA"
                + " -keysize 1024 -sigalg SHA1withRSA");

        kt("-exportcert -alias myca -rfc -file ca.pem");

        // import root CA to mycacerts keystore
        KeyStoreUtils.createCacerts("mycacerts", "ca.pem");

        kt("-gencrl -alias myca -id 0 -file ca.crl -sigalg MD5withRSA");

        patchcmd("printcrl", "-file ca.crl -trustcacerts")
                .shouldMatch("Verified by.*in cacerts");

        // Test -printcrl with root CA in local keystore
        kt("-gencrl -alias myca -id 0 -file ca.crl");

        kt("-printcrl -file ca.crl")
                .shouldMatch("Verified by.*in keystore");
    }
}
