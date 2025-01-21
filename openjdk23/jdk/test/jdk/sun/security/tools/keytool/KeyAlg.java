/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8029659 8214179 8267319
 * @summary Keytool, print key algorithm of certificate or key entry
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

public class KeyAlg {
    public static void main(String[] args) throws Exception {
        keytool("-genkeypair -alias ca -dname CN=CA -keyalg EC");
        keytool("-genkeypair -alias user -dname CN=User -keyalg RSA -keysize 1024");
        keytool("-certreq -alias user -file user.req");
        keytool("-gencert -alias ca -rfc -sigalg SHA1withECDSA"
                + " -infile user.req -outfile user.crt");
        keytool("-printcert -file user.crt")
                .shouldMatch("Signature algorithm name:.*SHA1withECDSA")
                .shouldMatch("Subject Public Key Algorithm:.*1024.*RSA");
        keytool("-genkeypair -alias g -dname CN=g -keyalg EC -keysize 256")
                .shouldContain("Generating 256-bit EC (secp256r1) key pair");
        keytool("-genkeypair -alias f -dname CN=f -keyalg EC")
                .shouldContain("Generating 384-bit EC (secp384r1) key pair");
    }

    static OutputAnalyzer keytool(String s) throws Exception {
        return SecurityTools.keytool(
                "-keystore ks -storepass changeit -keypass changeit " + s)
                .shouldHaveExitValue(0);
    }
}
