/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @test
 * @bug 8202598
 * @summary PEM outputs should have consistent line endings
 * @library /test/lib
 */

public class LineEndings {

    public static void main(String[] args) throws Exception {
        keytool("-genkeypair -dname CN=A -keyalg ec");

        keytool("-certreq -file a.csr -rfc");
        checkFile("a.csr");

        keytool("-exportcert -file a.crt -rfc");
        checkFile("a.crt");

        keytool("-gencrl -id 1 -rfc -file a.crl");
        checkFile("a.crl");

        // `keytool -printcrl` shows "Verified by ..." at the end. Remove it.
        String print = keytool("-printcrl -file a.crl -rfc").getStdout();
        print = print.substring(0, print.indexOf("Verified by"));
        Files.writeString(Path.of("print"), print);
        checkFile("print");
    }

    private static OutputAnalyzer keytool(String cmd) throws Exception {
        return SecurityTools.keytool(
                "-keystore ks -storepass changeit -alias a " + cmd)
                .shouldHaveExitValue(0);
    }

    // Make sure only CRLF is used inside the file.
    private static void checkFile(String name) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        for (byte b : Files.readAllBytes(Path.of(name))) {
            // Collect all non-printable bytes in an array
            if (b < 32) bout.write(b);
        }
        // There should only be a series of CRLFs left
        byte[] endings = bout.toByteArray();
        Asserts.assertTrue(endings.length > 4, "Too empty");
        Asserts.assertTrue(endings.length % 2 == 0,
                "Length is " + endings.length);
        for (int i = 0; i < endings.length; i += 2) {
            Asserts.assertEquals(endings[i], (byte)'\r',
                    "Byte at " + i + " is not CR");
            Asserts.assertEquals(endings[i + 1], (byte)'\n',
                    "Byte at " + (i + 1) + " is not LF");
        }
    }
}
