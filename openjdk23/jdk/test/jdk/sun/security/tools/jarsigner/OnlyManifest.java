/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7004035
 * @summary signed jar with only META-INF/* inside is not verifiable
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OnlyManifest {
    static OutputAnalyzer kt(String cmd) throws Exception {
        return SecurityTools.keytool("-storepass changeit -keypass changeit "
                + "-keystore ks -keyalg rsa " + cmd);
    }

    static void gencert(String owner, String cmd) throws Exception {
        kt("-certreq -alias " + owner + " -file tmp.req");
        kt("-gencert -infile tmp.req -outfile tmp.cert " + cmd);
        kt("-import -alias " + owner + " -file tmp.cert");
    }

    public static void main(String[] args) throws Exception {
        // Create an empty jar file with only MANIFEST.MF
        Files.write(Path.of("manifest"), List.of("Key: Value"));
        SecurityTools.jar("cvfm a.jar manifest");

        kt("-alias ca -dname CN=ca -genkey -validity 300 -ext bc:c")
                .shouldHaveExitValue(0);
        kt("-alias a -dname CN=a -genkey -validity 300")
                .shouldHaveExitValue(0);
        gencert("a", "-alias ca -validity 300");

        SecurityTools.jarsigner("-keystore ks -storepass changeit"
                + " a.jar a -debug -strict")
                .shouldHaveExitValue(0);
        SecurityTools.jarsigner("-keystore ks -storepass changeit"
                + " -verify a.jar a -debug -strict")
                .shouldHaveExitValue(0)
                .shouldNotContain("unsigned");
    }
}
