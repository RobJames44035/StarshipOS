/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8284194
 * @summary Allow empty subject fields in keytool
 * @library /test/lib
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;

import java.io.File;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

public class EmptyField {

    public static void main(String[] args) throws Exception {
        // All "." in first round, "Me" as name in 2nd round.
        SecurityTools.setResponse(
                ".\n.\n.\n.\n.\n.\n"        // all empty, must retry
                + "Me\n\n\n\n\n\nno\n"      // one non-empty, ask yes/no
                + "\n\n\n\n\n\nyes\n");     // remember input
        SecurityTools.keytool("-genkeypair -keystore ks -storepass changeit -alias b -keyalg EC")
                .shouldContain("[Unknown]") // old default
                .shouldContain("At least one field must be provided. Enter again.")
                .shouldContain("[]") // new value in 2nd round
                .shouldContain("[Me]") // new value in 3nd round
                .shouldContain("Is CN=Me correct?")
                .shouldHaveExitValue(0);
        var ks = KeyStore.getInstance(new File("ks"), "changeit".toCharArray());
        var cert = (X509Certificate) ks.getCertificate("b");
        Asserts.assertEQ(cert.getSubjectX500Principal().toString(), "CN=Me");
    }
}
