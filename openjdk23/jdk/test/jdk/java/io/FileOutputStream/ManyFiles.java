/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4189011 5019303
 * @summary Test opening over 2048 files
 * @run main/timeout=300 ManyFiles
 */

import java.io.*;
import java.util.*;

public class ManyFiles {
    static int count;
    static List files = new ArrayList();
    static List streams = new ArrayList();
    static int NUM_FILES = 2050;

    public static void main(String args[]) throws Exception {
        // Linux does not yet allow opening this many files; Solaris
        // 8 requires an explicit allocation of more file descriptors
        // to succeed. Since this test is written to check for a
        // Windows capability it is much simpler to only run it
        // on that platform.
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Linux"))
            return;

        for (int n = 0; n < NUM_FILES; n++) {
            File f = new File("file" + count++);
            files.add(f);
            streams.add(new FileOutputStream(f));
        }

        Iterator i = streams.iterator();
        while(i.hasNext()) {
            FileOutputStream fos = (FileOutputStream)i.next();
            fos.close();
        }

        i = files.iterator();
        while(i.hasNext()) {
            File f = (File)i.next();
            f.delete();
        }
    }
}
