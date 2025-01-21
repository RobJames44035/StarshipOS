/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4924188 8202816
 * @summary sign a JAR file that has entry names with non-ASCII characters.
 * @modules jdk.jartool/sun.security.tools.jarsigner
 * @run main/othervm JarSigningNonAscii
 */

import java.io.*;
import java.util.*;
import java.util.jar.*;
import java.security.cert.Certificate;

public class JarSigningNonAscii {

    private static String jarFile;
    private static String keystore;

    public static void main(String[] args) throws Exception {

        String srcDir = System.getProperty("test.src", ".");
        String destDir = System.getProperty("test.classes", ".");
        String unsignedJar = srcDir + "/JarSigning_RU.jar";
        String signedJar = destDir + "/JarSigning_RU.signed.jar";
        String keystore = srcDir + "/JarSigning.keystore";

        // remove signed jar if it exists
        try {
            File removeMe = new File(signedJar);
            removeMe.delete();
        } catch (Exception e) {
            // ignore
            e.printStackTrace();
        }

        // sign the provided jar file
        String[] jsArgs = {
                        "-keystore", keystore,
                        "-storepass", "bbbbbb",
                        "-signedJar", signedJar,
                        unsignedJar, "b"
                        };
        sun.security.tools.jarsigner.Main.main(jsArgs);

        //  verify the signed jar file

        /**
         * can not do this because JarSigner calls System.exit
         * with an exit code that jtreg does not like
         *
        String[] vArgs = {
                "-verify",
                "-keystore", keystore,
                "-storepass", "bbbbbb",
                "-verbose",
                signedJar
                };
        JarSigner.main(vArgs);
        */

        JarEntry je;
        JarFile jf = new JarFile(signedJar, true);

        Vector entriesVec = new Vector();
        byte[] buffer = new byte[8192];

        Enumeration entries = jf.entries();
        while (entries.hasMoreElements()) {
            je = (JarEntry)entries.nextElement();
            entriesVec.addElement(je);
            InputStream is = jf.getInputStream(je);
            int n;
            while ((n = is.read(buffer, 0, buffer.length)) != -1) {
                // we just read. this will throw a SecurityException
                // if  a signature/digest check fails.
            }
            is.close();
        }
        jf.close();
        Manifest man = jf.getManifest();
        int isSignedCount = 0;
        if (man != null) {
            Enumeration e = entriesVec.elements();
            while (e.hasMoreElements()) {
                je = (JarEntry) e.nextElement();
                String name = je.getName();
                Certificate[] certs = je.getCertificates();
                if ((certs != null) && (certs.length > 0)) {
                    isSignedCount++;
                }
            }
        }

        if (isSignedCount != 4) {
            throw new SecurityException("error signing JAR file");
        }

        System.out.println("jar verified");
    }
}
