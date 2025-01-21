/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4953126
 * @summary Check that a signed JAR file containing an unsupported signer info
 *          attribute can be parsed successfully.
 */

import java.io.File;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.*;

public class ScanSignedJar {

    public static void main(String[] args) throws Exception {
        boolean isSigned = false;
        try (JarFile file = new JarFile(new File(System.getProperty("test.src","."),
                 "bogus-signerinfo-attr.jar"))) {
            byte[] buffer = new byte[8192];

            for (Enumeration entries = file.entries(); entries.hasMoreElements();) {
                JarEntry entry = (JarEntry) entries.nextElement();
                try (InputStream jis = file.getInputStream(entry)) {
                    while (jis.read(buffer, 0, buffer.length) != -1) {
                        // read the jar entry
                    }
                }
                if (entry.getCertificates() != null) {
                    isSigned = true;
                }
                System.out.println((isSigned ? "[signed] " : "\t ") +
                    entry.getName());
            }
        }

        if (isSigned) {
            System.out.println("\nJAR file has signed entries");
        } else {
            throw new Exception("Failed to detect that the JAR file is signed");
        }
    }
}
