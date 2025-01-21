/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8229775
 * @summary Incorrect warning when jar was signed with -sectionsonly
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.util.JarUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class SectionsOnly {
    public static void main(String[] args) throws Exception {
        String common = "-storepass changeit -keypass changeit -keystore ks ";
        SecurityTools.keytool(common
                + "-keyalg rsa -genkeypair -alias me -dname CN=Me");
        JarUtils.createJarFile(Path.of("so.jar"), Path.of("."),
                Files.write(Path.of("so.txt"), new byte[0]));
        SecurityTools.jarsigner(common + "-sectionsonly so.jar me");
        SecurityTools.jarsigner(common + "-verify -verbose so.jar")
                .shouldNotContain("Unparsable signature-related file")
                .shouldHaveExitValue(0);
    }
}
