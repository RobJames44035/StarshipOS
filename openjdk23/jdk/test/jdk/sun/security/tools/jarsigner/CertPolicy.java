/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8036709
 * @summary Java 7 jarsigner displays warning about cert policy tree
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.util.JarUtils;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CertPolicy {
    static OutputAnalyzer keytool(String cmd) throws Exception {
        return SecurityTools.keytool("-keypass changeit -storepass changeit "
                + "-keystore ks -keyalg rsa " + cmd);
    }

    static OutputAnalyzer jarsigner(String cmd) throws Exception {
        return SecurityTools.jarsigner("-storepass changeit -keystore ks " + cmd);
    }

    public static void main(String[] args) throws Exception {

        keytool("-genkeypair -alias ca -dname CN=CA -ext bc");
        keytool("-genkeypair -alias int -dname CN=Int");
        keytool("-genkeypair -alias ee -dname CN=EE");

        // CertificatePolicies [[PolicyId: [1.2.3]], [PolicyId: [1.2.4]]]
        // PolicyConstraints: [Require: 0; Inhibit: unspecified]
        keytool("-certreq -alias int -file int.req");
        keytool("-gencert -rfc -alias ca "
                + "-ext 2.5.29.32=300C300406022A03300406022A04 "
                + "-ext 2.5.29.36=3003800100 "
                + "-ext bc -infile int.req -outfile int.cert");
        keytool("-import -alias int -file int.cert");

        // CertificatePolicies [[PolicyId: [1.2.3]]]
        keytool("-certreq -alias ee -file ee.req");
        keytool("-gencert -rfc -alias int -ext 2.5.29.32=3006300406022A03 "
                + "-infile ee.req -outfile ee.cert");
        keytool("-import -alias ee -file ee.cert");

        Files.write(Path.of("cc"), List.of(
                keytool("-export -alias ee -rfc").getOutput(),
                keytool("-export -alias int -rfc").getOutput(),
                keytool("-export -alias ca -rfc").getOutput()));

        keytool("-delete -alias int");

        JarUtils.createJarFile(Path.of("a.jar"), Path.of("."), Path.of("cc"));

        // Make sure the certchain in the signed jar contains all 3 certs
        jarsigner("-strict -certchain cc a.jar ee -debug")
                .shouldHaveExitValue(0);

        jarsigner("-strict -verify a.jar -debug")
                .shouldHaveExitValue(0);
    }
}
