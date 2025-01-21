/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6948909 8217375
 * @summary Jarsigner removes MANIFEST.MF info for badly packages jar's
 * @library /test/lib
 */
/*
 * See also InsufficientSectionDelimiter.java for similar tests including cases
 * without or with different line breaks.
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DiffEnd {

    static void check() throws Exception {
        String ksArgs = "-keystore " + Path.of(System.getProperty("test.src"))
                .resolve("JarSigning.keystore") + " -storepass bbbbbb";

        SecurityTools.jarsigner(ksArgs + " -digestalg SHA-256 "
                + "-signedjar diffend.signed.jar diffend.jar c")
                .shouldHaveExitValue(0);
        SecurityTools.jarsigner(" -verify " + ksArgs + " -verbose "
                + "diffend.signed.jar c")
                .stdoutShouldMatch("^smk .* 1$").shouldHaveExitValue(0);

        try (JarFile jf = new JarFile("diffend.signed.jar")) {
            Asserts.assertTrue(jf.getManifest().getMainAttributes()
                    .containsKey(new Attributes.Name("Today")));
        }
    }

    public static void main(String[] args) throws Exception {
        // A MANIFEST.MF using \n as newlines and no double newlines at the end
        byte[] manifest = ("Manifest-Version: 1.0\n"
                        + "Created-By: 1.7.0-internal (Sun Microsystems Inc.)\n"
                        + "Today: Monday\n").getBytes(StandardCharsets.UTF_8);

        // Without the fake .RSA file, to trigger the if (wasSigned) else block
        try (FileOutputStream fos = new FileOutputStream("diffend.jar");
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));
            zos.write(manifest);
            zos.putNextEntry(new ZipEntry("1"));
            zos.write(new byte[10]);
        }
        check();

        // With the fake .RSA file, to trigger the if (wasSigned) block
        try (FileOutputStream fos = new FileOutputStream("diffend.jar");
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));
            zos.write(manifest);
            zos.putNextEntry(new ZipEntry("META-INF/x.RSA")); // fake .RSA
            zos.putNextEntry(new ZipEntry("1"));
            zos.write(new byte[10]);
        }
        check();
    }
}
