/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6561126 8267319
 * @modules jdk.jartool/jdk.security.jarsigner
 * @summary keytool should use larger default keysize for keypairs
 * @library /test/lib
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;
import jdk.test.lib.util.JarUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import jdk.security.jarsigner.JarSigner;

public class NewSize7 {

    private static final String DEF_DIGEST_ALGO =
            JarSigner.Builder.getDefaultDigestAlgorithm();

    public static void main(String[] args) throws Exception {
        String common = "-storepass changeit -keypass changeit -keystore ks ";
        SecurityTools.keytool(common
                + "-keyalg rsa -genkeypair -alias me -dname CN=Me");
        Files.write(Path.of("ns7.txt"), new byte[0]);
        JarUtils.createJarFile(Path.of("ns7.jar"), Path.of("."),
                Path.of("ns7.txt"));
        SecurityTools.jarsigner(common + "ns7.jar me");

        try (JarFile jf = new JarFile("ns7.jar")) {
            try (InputStream is = jf.getInputStream(
                    jf.getEntry("META-INF/MANIFEST.MF"))) {
                Asserts.assertTrue(new Manifest(is).getAttributes("ns7.txt")
                        .keySet().stream()
                        .anyMatch(s -> s.toString().contains(DEF_DIGEST_ALGO)));
            }
            try (InputStream is = jf.getInputStream(
                    jf.getEntry("META-INF/ME.SF"))) {
                Asserts.assertTrue(new Manifest(is).getAttributes("ns7.txt")
                        .keySet().stream()
                        .anyMatch(s -> s.toString().contains(DEF_DIGEST_ALGO)));
            }
        }
    }
}
