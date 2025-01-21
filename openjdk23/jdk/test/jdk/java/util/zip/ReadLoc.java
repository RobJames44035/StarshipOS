/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4766057
 * @summary Test for VM crash in getEntry
 */

import java.util.zip.*;
import java.util.*;
import java.io.*;

public class ReadLoc {
    public static void main(String[] args) throws Exception {
        int iterations = 0;
        File zFile1 = new File(System.getProperty("test.src", "."),
                               "pkware123456789012345.zip");
        while (iterations < 2500) {
            ZipFile zipFile = new ZipFile(zFile1);
            List entries = Collections.list(zipFile.entries());
            for (Iterator it = entries.iterator(); it.hasNext();) {
                ZipEntry zipEntry = (ZipEntry)it.next();
                InputStream in = zipFile.getInputStream(zipEntry);
                in.close();
            }
            iterations++;
        }
    }
}
