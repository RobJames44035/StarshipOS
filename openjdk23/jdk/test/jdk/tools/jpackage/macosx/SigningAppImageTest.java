/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.nio.file.Path;

import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.Annotations.Parameter;
import jdk.jpackage.test.AdditionalLauncher;

/**
 * Tests generation of app image with --mac-sign and related arguments. Test will
 * generate app image and verify signature of main launcher and app bundle itself.
 * This test requires that machine is configured with test certificate for
 * "Developer ID Application: jpackage.openjdk.java.net" or alternately
 * "Developer ID Application: " + name specified by system property:
 * "jpackage.mac.signing.key.user.name"
 * in the jpackagerTest keychain (or alternately the keychain specified with
 * the system property "jpackage.mac.signing.keychain".
 * If this certificate is self-signed, it must have be set to
 * always allowed access to this keychain" for user which runs test.
 * (If cert is real (not self signed), the do not set trust to allow.)
 */

/*
 * @test
 * @summary jpackage with --type app-image --mac-sign
 * @library /test/jdk/tools/jpackage/helpers
 * @library /test/lib
 * @library base
 * @build SigningBase
 * @build SigningCheck
 * @build jtreg.SkippedException
 * @build jdk.jpackage.test.*
 * @build SigningAppImageTest
 * @requires (os.family == "mac")
 * @run main/othervm/timeout=720 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=SigningAppImageTest
 */
public class SigningAppImageTest {

    @Test
    // ({"sign or not", "signing-key or sign-identity", "certificate index"})
    // Sign, signing-key and ASCII certificate
    @Parameter({"true", "true", SigningBase.ASCII_INDEX})
    // Sign, signing-key and UNICODE certificate
    @Parameter({"true", "true", SigningBase.UNICODE_INDEX})
    // Sign, signing-indentity and UNICODE certificate
    @Parameter({"true", "false", SigningBase.UNICODE_INDEX})
    // Unsigned
    @Parameter({"false", "true", "-1"})
    public void test(String... testArgs) throws Exception {
        boolean doSign = Boolean.parseBoolean(testArgs[0]);
        boolean signingKey = Boolean.parseBoolean(testArgs[1]);
        int certIndex = Integer.parseInt(testArgs[2]);

        SigningCheck.checkCertificates(certIndex);

        JPackageCommand cmd = JPackageCommand.helloAppImage();
        if (doSign) {
            cmd.addArguments("--mac-sign",
                    "--mac-signing-keychain",
                    SigningBase.getKeyChain());
            if (signingKey) {
                cmd.addArguments("--mac-signing-key-user-name",
                        SigningBase.getDevName(certIndex));
            } else {
                cmd.addArguments("--mac-app-image-sign-identity",
                        SigningBase.getAppCert(certIndex));
            }
        }
        AdditionalLauncher testAL = new AdditionalLauncher("testAL");
        testAL.applyTo(cmd);
        cmd.executeAndAssertHelloAppImageCreated();

        Path launcherPath = cmd.appLauncherPath();
        SigningBase.verifyCodesign(launcherPath, doSign, certIndex);

        Path testALPath = launcherPath.getParent().resolve("testAL");
        SigningBase.verifyCodesign(testALPath, doSign, certIndex);

        Path appImage = cmd.outputBundle();
        SigningBase.verifyCodesign(appImage, doSign, certIndex);
        if (doSign) {
            SigningBase.verifySpctl(appImage, "exec", certIndex);
        }
    }
}
