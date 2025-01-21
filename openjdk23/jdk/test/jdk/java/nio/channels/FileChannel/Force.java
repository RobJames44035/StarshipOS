/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
   @bug 4434115 4802789
 * @summary Check for regressions in FileChannel.force
 */

import java.io.*;
import java.nio.*;
import java.nio.channels.*;


public class Force {
    public static void main(String[] args) throws Exception {
        writeAfterForce();
        forceReadableOnly();
    }

    // 4434115: FileChannel.write() fails when preceded by force() operation
    private static void writeAfterForce() throws Exception {
        byte[] srcData = new byte[20];
        File blah = File.createTempFile("blah", null);
        blah.deleteOnExit();
        FileOutputStream fis = new FileOutputStream(blah);
        FileChannel fc = fis.getChannel();
        fc.write(ByteBuffer.wrap(srcData));
        fc.force(false);
        fc.write(ByteBuffer.wrap(srcData));
        fc.close();
    }

    // 4802789: FileChannel.force(true) throws IOException (windows)
    private static void forceReadableOnly() throws Exception {
        File f = File.createTempFile("blah", null);
        f.deleteOnExit();
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
        fc.force(true);
        fc.close();
        fis.close();
    }
}
