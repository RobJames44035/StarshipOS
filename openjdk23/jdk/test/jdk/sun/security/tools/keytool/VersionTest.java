/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8272163
 * @summary keytool -version test
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;

public class VersionTest {

    public static void main(String[] args) throws Exception {
        SecurityTools.keytool("-version")
                .shouldContain("keytool")
                .shouldHaveExitValue(0);

        SecurityTools.keytool("-version -erropt")
                .shouldContain("Illegal option:  -erropt")
                .shouldContain("Prints the program version")
                .shouldContain("Use \"keytool -?, -h, or --help\" for this help message")
                .shouldHaveExitValue(1);

        SecurityTools.keytool("-genkeypair -erropt")
                .shouldContain("Illegal option:  -erropt")
                .shouldContain("Generates a key pair")
                .shouldContain("Use \"keytool -?, -h, or --help\" for this help message")
                .shouldHaveExitValue(1);

        SecurityTools.keytool("-version --help")
                .shouldContain("Prints the program version")
                .shouldContain("Use \"keytool -?, -h, or --help\" for this help message")
                .shouldHaveExitValue(0);

        SecurityTools.keytool("--help -version")
                .shouldContain("Prints the program version")
                .shouldContain("Use \"keytool -?, -h, or --help\" for this help message")
                .shouldHaveExitValue(0);

        SecurityTools.keytool("-genkeypair --help")
                .shouldContain("Generates a key pair")
                .shouldContain("Use \"keytool -?, -h, or --help\" for this help message")
                .shouldHaveExitValue(0);

        SecurityTools.keytool("--help")
                .shouldContain("-genkeypair         Generates a key pair")
                .shouldContain("-version            Prints the program version")
                .shouldHaveExitValue(0);
    }
}
