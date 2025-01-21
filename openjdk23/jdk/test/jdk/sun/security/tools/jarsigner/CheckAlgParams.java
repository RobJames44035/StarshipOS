/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8277474 8283665
 * @summary jarsigner -verify should check if the algorithm parameters of
 *          its signature algorithm use disabled or legacy algorithms
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.util.JarUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckAlgParams {
    private static final String JAVA_SECURITY_FILE = "java.security";

    public static void main(String[] args) throws Exception{

        SecurityTools.keytool("-keystore ks -storepass changeit " +
                "-genkeypair -keyalg RSASSA-PSS -alias ca -dname CN=CA " +
                "-ext bc:c")
                .shouldHaveExitValue(0);

        JarUtils.createJarFile(Path.of("a.jar"), Path.of("."), Path.of("ks"));

        SecurityTools.jarsigner("-keystore ks -storepass changeit " +
                "-signedjar signeda.jar " +
                "-verbose" +
                " a.jar ca")
                .shouldHaveExitValue(0);

        Files.writeString(Files.createFile(Paths.get(JAVA_SECURITY_FILE)),
                "jdk.jar.disabledAlgorithms=SHA384\n" +
                "jdk.security.legacyAlgorithms=\n");

        SecurityTools.jarsigner("-verify signeda.jar " +
                "-J-Djava.security.properties=" +
                JAVA_SECURITY_FILE +
                " -keystore ks -storepass changeit -verbose -debug")
                .shouldMatch("Digest algorithm: SHA-384.*(disabled)")
                .shouldMatch("Signature algorithm: RSASSA-PSS using PSSParameterSpec.*hashAlgorithm=SHA-384.*(disabled)")
                .shouldContain("The jar will be treated as unsigned")
                .shouldHaveExitValue(0);

        Files.deleteIfExists(Paths.get(JAVA_SECURITY_FILE));
        Files.writeString(Files.createFile(Paths.get(JAVA_SECURITY_FILE)),
                "jdk.jar.disabledAlgorithms=\n" +
                "jdk.security.legacyAlgorithms=SHA384\n");

        SecurityTools.jarsigner("-verify signeda.jar " +
                "-J-Djava.security.properties=" +
                JAVA_SECURITY_FILE +
                " -keystore ks -storepass changeit -verbose -debug")
                .shouldMatch("Digest algorithm: SHA-384.*(weak)")
                .shouldMatch("Signature algorithm: RSASSA-PSS using PSSParameterSpec.*hashAlgorithm=SHA-384.*(weak)")
                .shouldNotContain("The jar will be treated as unsigned")
                .shouldHaveExitValue(0);
    }
}
