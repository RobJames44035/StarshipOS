/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8021788
 * @summary JarInputStream doesn't provide certificates for some file under META-INF
 * @modules java.base/sun.security.tools.keytool
 *          jdk.jartool/sun.security.tools.jarsigner
 */

import java.util.jar.*;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExtraFileInMetaInf {
    public static void main(String args[]) throws Exception {

        // Create a zip file with 2 entries
        try (ZipOutputStream zos =
                     new ZipOutputStream(new FileOutputStream("x.jar"))) {
            zos.putNextEntry(new ZipEntry("META-INF/SUB/file"));
            zos.write(new byte[10]);
            zos.putNextEntry(new ZipEntry("x"));
            zos.write(new byte[10]);
            zos.close();
        }

        // Sign it
        new File("ks").delete();
        sun.security.tools.keytool.Main.main(
                ("-keystore ks -storepass changeit -keypass changeit " +
                        "-keyalg rsa -alias a -dname CN=A -genkeypair").split(" "));
        sun.security.tools.jarsigner.Main.main(
                "-keystore ks -storepass changeit x.jar a".split(" "));

        // Check if the entries are signed
        try (JarInputStream jis =
                     new JarInputStream(new FileInputStream("x.jar"))) {
            JarEntry je;
            while ((je = jis.getNextJarEntry()) != null) {
                String name = je.toString();
                if (name.equals("META-INF/SUB/file") || name.equals("x")) {
                    while (jis.read(new byte[1000]) >= 0);
                    if (je.getCertificates() == null) {
                        throw new Exception(name + " not signed");
                    }
                }
            }
        }
    }
}
