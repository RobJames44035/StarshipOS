/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8272163
 * @summary jarsigner -version test
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.util.JarUtils;
import java.nio.file.Path;

public class VersionTest {

    public static void main(String[] args) throws Exception {
        SecurityTools.jarsigner("-version")
                .shouldContain("jarsigner")
                .shouldHaveExitValue(0);

        SecurityTools.jarsigner("-version -erropt")
                .shouldContain("Illegal option: -erropt")
                .shouldContain("Please type jarsigner --help for usage")
                .shouldHaveExitValue(1);

        SecurityTools.jarsigner("-verify -erropt")
                .shouldContain("Illegal option: -erropt")
                .shouldContain("Please type jarsigner --help for usage")
                .shouldHaveExitValue(1);

        SecurityTools.jarsigner("-version --help")
                .shouldContain("Usage: jarsigner [options] jar-file alias")
                .shouldContain("[-verify]                   verify a signed JAR file")
                .shouldContain("[-version]                  print the program version")
                .shouldHaveExitValue(0);

        SecurityTools.jarsigner("--help -version")
                .shouldContain("Usage: jarsigner [options] jar-file alias")
                .shouldContain("[-verify]                   verify a signed JAR file")
                .shouldContain("[-version]                  print the program version")
                .shouldHaveExitValue(0);

        SecurityTools.jarsigner("-verify --help")
                .shouldContain("Usage: jarsigner [options] jar-file alias")
                .shouldContain("[-verify]                   verify a signed JAR file")
                .shouldContain("[-version]                  print the program version")
                .shouldHaveExitValue(0);

        SecurityTools.jarsigner("--help")
                .shouldContain("Usage: jarsigner [options] jar-file alias")
                .shouldContain("[-verify]                   verify a signed JAR file")
                .shouldContain("[-version]                  print the program version")
                .shouldHaveExitValue(0);

        SecurityTools.jarsigner()
                .shouldContain("Usage: jarsigner [options] jar-file alias")
                .shouldContain("[-verify]                   verify a signed JAR file")
                .shouldContain("[-version]                  print the program version")
                .shouldHaveExitValue(0);

        SecurityTools.keytool("-genkeypair -keystore ks -storepass changeit" +
                " -keyalg rsa -dname CN=ee -alias ee")
                .shouldHaveExitValue(0);

        JarUtils.createJarFile(Path.of("a.jar"), Path.of("."), Path.of("."));

        /*
         * -version is specified but -help is not specified, jarsigner
         * will only print the program version and ignore other options.
         */
        SecurityTools.jarsigner("-keystore ks -storepass changeit" +
                " -signedjar signeda.jar a.jar ee -version")
                .shouldNotContain("jar signed.")
                .shouldContain("jarsigner ")
                .shouldHaveExitValue(0);

        /*
         * -version is specified but -help is not specified, jarsigner
         * will only print the program version and ignore other options.
         */
        SecurityTools.jarsigner("-version -verify a.jar")
                .shouldNotContain("jar is unsigned.")
                .shouldContain("jarsigner ")
                .shouldHaveExitValue(0);
    }
}
