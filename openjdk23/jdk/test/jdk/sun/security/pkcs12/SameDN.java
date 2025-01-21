/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import static jdk.test.lib.SecurityTools.keytool;

import java.io.File;
import java.security.KeyStore;

/*
 * @test
 * @bug 8215776
 * @library /test/lib
 * @summary Keytool importkeystore may mix up certificate chain entries when DNs conflict
 */
public class SameDN {

    private static final String COMMON = "-keystore ks -storepass changeit ";

    public static final void main(String[] args) throws Exception {
        genkeypair("ca1", "CN=CA");
        genkeypair("ca2", "CN=CA");
        genkeypair("user1", "CN=user");
        genkeypair("user2", "CN=user");
        gencert("ca1", "user1");
        gencert("ca2", "user2");

        KeyStore ks = KeyStore.getInstance(
                new File("ks"), "changeit".toCharArray());
        if (!ks.getCertificate("ca1").equals(ks.getCertificateChain("user1")[1])) {
            throw new Exception("user1 not signed by ca1");
        }
        if (!ks.getCertificate("ca2").equals(ks.getCertificateChain("user2")[1])) {
            throw new Exception("user2 not signed by ca2");
        }
    }

    static void genkeypair(String alias, String dn) throws Exception {
        keytool(COMMON + "-genkeypair -keyalg DSA -alias " + alias + " -dname " + dn)
                .shouldHaveExitValue(0);
    }

    static void gencert(String issuer, String subject) throws Exception {
        keytool(COMMON + "-certreq -alias " + subject + " -file req")
                .shouldHaveExitValue(0);
        keytool(COMMON + "-gencert -alias " + issuer + " -infile req -outfile cert")
                .shouldHaveExitValue(0);
        keytool(COMMON + "-importcert -alias " + subject + " -file cert")
                .shouldHaveExitValue(0);
    }
}
