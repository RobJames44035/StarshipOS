/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6712755
 * @summary jarsigner fails to sign itextasian.jar since 1.5.0_b14,
 *          it works with 1.5.0_13
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;

import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class EmptyManifest {

    public static void main(String[] args) throws Exception {

        try (FileOutputStream fos = new FileOutputStream("em.jar");
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
            zos.write(new byte[]{'\r', '\n'});
            zos.putNextEntry(new ZipEntry("A"));
            zos.write(new byte[10]);
            zos.putNextEntry(new ZipEntry("B"));
            zos.write(new byte[0]);
        }

        SecurityTools.keytool("-keystore ks -storepass changeit "
                + "-keypass changeit -alias a -dname CN=a -keyalg rsa "
                + "-genkey -validity 300");

        SecurityTools.jarsigner("-keystore ks -storepass changeit em.jar a")
                .shouldHaveExitValue(0);
        SecurityTools.jarsigner("-keystore ks -storepass changeit -verify "
                + "-debug -strict em.jar")
                .shouldHaveExitValue(0);
    }
}
