/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4462336 6799037
 * @summary Simple MappedByteBuffer tests
 */

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Basic {
    public static void main(String[] args) throws Exception {
        byte[] srcData = new byte[20];
        for (int i=0; i<20; i++)
            srcData[i] = 3;
        File blah = File.createTempFile("blah", null);
        blah.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(blah);
        FileChannel fc = fos.getChannel();
        fc.write(ByteBuffer.wrap(srcData));
        fc.close();
        fos.close();

        FileInputStream fis = new FileInputStream(blah);
        fc = fis.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, 10);
        mbb.load();
        mbb.isLoaded();
        mbb.force();
        if (!mbb.isReadOnly())
            throw new RuntimeException("Incorrect isReadOnly");

        // repeat with unaligned position in file
        mbb = fc.map(FileChannel.MapMode.READ_ONLY, 1, 10);
        mbb.load();
        mbb.isLoaded();
        mbb.force();
        fc.close();
        fis.close();

        RandomAccessFile raf = new RandomAccessFile(blah, "r");
        fc = raf.getChannel();
        mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, 10);
        if (!mbb.isReadOnly())
            throw new RuntimeException("Incorrect isReadOnly");
        fc.close();
        raf.close();

        raf = new RandomAccessFile(blah, "rw");
        fc = raf.getChannel();
        mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        if (mbb.isReadOnly())
            throw new RuntimeException("Incorrect isReadOnly");
        fc.close();
        raf.close();

        // clean-up
        mbb = null;
        System.gc();
        Thread.sleep(500);
    }
}
