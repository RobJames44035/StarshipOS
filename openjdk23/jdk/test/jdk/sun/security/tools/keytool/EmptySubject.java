/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6847026
 * @summary keytool should be able to generate certreq and cert without subject name
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmptySubject{
    static final String KS = "emptysubject.jks";
    public static void main(String[] args) throws Exception {
        kt("-alias", "ca", "-dname", "CN=CA", "-genkeypair");
        kt("-alias", "me", "-dname", "CN=Me", "-genkeypair");

        // When -dname is recognized, SAN must be specified, otherwise,
        // -printcert fails.
        kt("-alias", "me", "-certreq", "-dname", "", "-file", "me1.req")
                .shouldHaveExitValue(0);
        kt("-alias", "ca", "-gencert",
                "-infile", "me1.req", "-outfile", "me1.crt")
                .shouldHaveExitValue(0);
        kt("-printcert", "-file", "me1.crt").shouldNotHaveExitValue(0);

        kt("-alias", "me", "-certreq", "-file", "me2.req")
                .shouldHaveExitValue(0);
        kt("-alias", "ca", "-gencert", "-dname", "",
                "-infile", "me2.req", "-outfile", "me2.crt")
                .shouldHaveExitValue(0);
        kt("-printcert", "-file", "me2.crt").shouldNotHaveExitValue(0);

        kt("-alias", "me", "-certreq", "-dname", "", "-file", "me3.req")
                .shouldHaveExitValue(0);
        kt("-alias", "ca", "-gencert", "-ext", "san:c=email:me@me.com",
                "-infile", "me3.req", "-outfile", "me3.crt")
                .shouldHaveExitValue(0);
        kt("-printcert", "-file", "me3.crt").shouldHaveExitValue(0);

        kt("-alias", "me", "-certreq", "-file", "me4.req")
                .shouldHaveExitValue(0);
        kt("-alias", "ca", "-gencert", "-dname", "",
                "-ext", "san:c=email:me@me.com",
                "-infile", "me4.req", "-outfile", "me4.crt")
                .shouldHaveExitValue(0);
        kt("-printcert", "-file", "me4.crt").shouldHaveExitValue(0);
    }

    static OutputAnalyzer kt(String... s) throws Exception {
        List<String> cmd = new ArrayList<>();
        cmd.addAll(Arrays.asList(
                "-storepass", "changeit",
                "-keypass", "changeit",
                "-keystore", KS,
                "-keyalg", "rsa"));
        cmd.addAll(Arrays.asList(s));
        return SecurityTools.keytool(cmd);
    }
}
