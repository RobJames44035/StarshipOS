/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8049834
 * @summary Two security tools tests do not run with only JRE
 * @library /test/lib
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.util.JarUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarFile;

public class DefaultOptions {

    static OutputAnalyzer jarsigner(String cmd) throws Throwable {
        ProcessBuilder pb = SecurityTools.getProcessBuilder(
                "jarsigner", List.of(cmd.trim().split("\\s+")));
        pb.environment().put("PASS", "changeit");
        return ProcessTools.executeCommand(pb);
    }

    static OutputAnalyzer keytool(String cmd) throws Throwable {
        cmd = "-storepass:env PASS -keypass:env PASS -keystore ks " + cmd;
        ProcessBuilder pb = SecurityTools.getProcessBuilder(
                "keytool", List.of(cmd.trim().split("\\s+")));
        pb.environment().put("PASS", "changeit");
        return ProcessTools.executeCommand(pb);
    }

    public static void main(String[] args) throws Throwable {
        keytool("-genkeypair -dname CN=A -alias a -keyalg rsa")
                .shouldHaveExitValue(0);
        keytool("-genkeypair -dname CN=CA -alias ca -keyalg rsa -ext bc:c")
                .shouldHaveExitValue(0);
        keytool("-alias a -certreq -file a.req");
        keytool("-alias ca -gencert -infile a.req -outfile a.cert");
        keytool("-alias a -import -file a.cert").shouldHaveExitValue(0);

        Files.write(Path.of("js.conf"), List.of(
                "jarsigner.all = -keystore ${user.dir}/ks -storepass:env PASS "
                        + "-debug -strict",
                "jarsigner.sign = -digestalg SHA-512",
                "jarsigner.verify = -verbose:summary"));

        JarUtils.createJarFile(Path.of("a.jar"), Path.of("."),
                Path.of("ks"), Path.of("js.conf"));

        jarsigner("-conf js.conf a.jar a").shouldHaveExitValue(0);
        jarsigner("-conf js.conf -verify a.jar").shouldHaveExitValue(0)
                .shouldContain("and 1 more");

        try (JarFile jf = new JarFile("a.jar")) {
            Asserts.assertTrue(jf.getManifest().getAttributes("ks").keySet()
                    .stream()
                    .anyMatch(s -> s.toString().contains("SHA-512-Digest")));
        }
    }
}
