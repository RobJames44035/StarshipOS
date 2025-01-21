/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8273236
 * @summary Test SHA1 usage SignedJAR
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestSha1Usage {

    static OutputAnalyzer kt(String cmd, String ks) throws Exception {
        return SecurityTools.keytool("-storepass changeit " + cmd +
                " -keystore " + ks);
    }

    public static void main(String[] args) throws Exception {

        SecurityTools.keytool("-keystore ks -storepass changeit " +
                "-genkeypair -keyalg rsa -alias ca -dname CN=CA " +
                "-ext eku=codeSigning -sigalg SHA1withRSA")
                .shouldContain("Warning:")
                .shouldMatch("The generated certificate.*SHA1withRSA.*considered a security risk")
                .shouldMatch("cannot be used to sign JARs")
                .shouldHaveExitValue(0);

        kt("-genkeypair -keyalg rsa -alias e1 -dname CN=E1", "ks");
        kt("-certreq -alias e1 -file tmp.req", "ks");
        SecurityTools.keytool("-keystore ks -storepass changeit " +
                "-gencert -alias ca -infile tmp.req -outfile tmp.cert")
                .shouldContain("Warning:")
                .shouldMatch("The issuer.*SHA1withRSA.*considered a security risk")
                .shouldMatch("cannot be used to sign JARs")
                .shouldHaveExitValue(0);
    }
}
