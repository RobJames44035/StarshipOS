/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/* @test
 * @bug 6304463
 * @summary Test whether the zip file still can be read after thread is interrupted
 */

import java.io.*;
import java.util.zip.*;

public class InterruptibleZip {

    public static void main(String[] args) throws Exception {
        /* Interrupt the current thread. The is.read call below
           should continue reading input.jar.
        */
        Thread.currentThread().interrupt();
        ZipFile zf = new ZipFile(new File(System.getProperty("test.src", "."), "input.jar"));
        ZipEntry ze = zf.getEntry("Available.java");
        InputStream is = zf.getInputStream(ze);
        byte[] buf = new byte[512];
        int n = is.read(buf);
        boolean interrupted = Thread.interrupted();
        System.out.printf("interrupted=%s n=%d name=%s%n",
                          interrupted, n, ze.getName());
        if (! interrupted) {
            throw new Error("Wrong interrupt status");
        }
        if (n != buf.length) {
            throw new Error("Read error");
        }
    }
}
