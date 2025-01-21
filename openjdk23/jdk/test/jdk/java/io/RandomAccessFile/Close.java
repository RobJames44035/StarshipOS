/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
   @bug 4710771
 * @summary Test RandomAccessFile.close
 */

import java.io.*;
import java.nio.channels.*;

public class Close {
    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("blah", null);
        f.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(f, "r");
        FileChannel channel = raf.getChannel();
        raf.close();
        if (channel.isOpen()) {
            throw new RuntimeException("Channel not closed");
        }
    }
}
