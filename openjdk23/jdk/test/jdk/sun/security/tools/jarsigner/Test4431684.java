/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 4431684
 * @summary jar signature certificate key usage check incorrect
 * @library /test/lib
 * @run main/othervm Test4431684
 */

import java.util.jar.*;
import java.util.*;
import java.io.*;

import jdk.test.lib.security.SecurityUtils;

public class Test4431684 {

    public static void main(String[] args) throws Exception {

        // Re-enable SHA1 since JavaApplication1.jar uses it
        SecurityUtils.removeFromDisabledAlgs("jdk.jar.disabledAlgorithms",
            List.of("SHA1"));
        File f = new File(System.getProperty("test.src", "."),
                          "JavaApplication1.jar");
        JarFile jf = new JarFile(f);
        Enumeration entries = jf.entries();
        while (entries.hasMoreElements()) {
            JarEntry je = (JarEntry)entries.nextElement();
            if(je.getName().endsWith("class")) {
                byte[] buffer = new byte[8192];
                InputStream is = jf.getInputStream(je);
                int n;
                while ((n = is.read(buffer, 0, buffer.length)) != -1) {
                }
                is.close();
                if(je.getCodeSigners() == null) {
                    throw new RuntimeException("FAIL: Cannot get code signers");
                }
            }
        }
    }
}
