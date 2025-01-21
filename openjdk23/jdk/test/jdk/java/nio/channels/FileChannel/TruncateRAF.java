/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/* @test
 * @bug 8204310
 * @summary Check how FileChannel behaves if the file size/offset change via
 *          RAF.setLength() and other methods.
 * @run main TruncateRAF
 */

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TruncateRAF {

    static void checkState(RandomAccessFile raf, FileChannel fch,
            long expectedOffset, long expectedLength)
        throws IOException
    {
        long rafLength = raf.length();
        long rafOffset = raf.getFilePointer();
        long fchLength = fch.size();
        long fchOffset = fch.position();

        if (rafLength != expectedLength)
            throw new RuntimeException("rafLength (" + rafLength + ") != " +
                    "expectedLength (" + expectedLength + ")");
        if (rafOffset != expectedOffset)
            throw new RuntimeException("rafOffset (" + rafOffset + ") != " +
                    "expectedOffset (" + expectedOffset + ")");
        if (fchLength != expectedLength)
            throw new RuntimeException("fchLength (" + fchLength + ") != " +
                    "expectedLength (" + expectedLength + ")");
        if (fchOffset != expectedOffset)
            throw new RuntimeException("fchOffset (" + fchOffset + ") != " +
                    "expectedOffset (" + expectedOffset + ")");
    }

    public static void main(String[] args) throws Throwable {
        File file = new File("tmp");
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             FileChannel fch = raf.getChannel()) {

            // initially empty
            checkState(raf, fch, 0, 0);

            // seeking beyond EOF
            raf.seek(42);
            checkState(raf, fch, 42, 0);

            // seeking beyond EOF
            fch.position(84);
            checkState(raf, fch, 84, 0);

            // writing at offset beyond EOF
            raf.write(1);
            checkState(raf, fch, 85, 85);

            // truncating
            raf.setLength(63);
            checkState(raf, fch, 63, 63);

            // writing at EOF
            fch.write(ByteBuffer.wrap(new byte[1]));
            checkState(raf, fch, 64, 64);

            // seeking at the middle
            fch.position(32);
            checkState(raf, fch, 32, 64);

            // truncating beyond offset
            fch.truncate(42);
            checkState(raf, fch, 32, 42);

            // truncating before offset
            fch.truncate(16);
            checkState(raf, fch, 16, 16);

            // writing at position beyond EOF
            fch.write(ByteBuffer.wrap(new byte[1]), 127);
            checkState(raf, fch, 16, 128);

            // writing at position before EOF
            fch.write(ByteBuffer.wrap(new byte[1]), 42);
            checkState(raf, fch, 16, 128);

            // truncating
            raf.setLength(64);
            checkState(raf, fch, 16, 64);

            // changing offset
            raf.seek(21);
            checkState(raf, fch, 21, 64);

            // skipping should change offset
            raf.skipBytes(4);
            checkState(raf, fch, 25, 64);

            // reading should change offset
            raf.read();
            checkState(raf, fch, 26, 64);

            // truncating to zero
            raf.setLength(0);
            checkState(raf, fch, 0, 0);

            // FileChannel cannot expand size
            fch.truncate(42);
            checkState(raf, fch, 0, 0);

            // expanding
            raf.setLength(42);
            checkState(raf, fch, 0, 42);

            // seeking beyond EOF
            raf.seek(512);
            checkState(raf, fch, 512, 42);

            // truncating to the same size
            fch.truncate(256);
            checkState(raf, fch, 256, 42);

            // truncating to the same size
            fch.truncate(42);
            checkState(raf, fch, 42, 42);

            // truncating to zero
            fch.truncate(0);
            checkState(raf, fch, 0, 0);
        }
    }
}
