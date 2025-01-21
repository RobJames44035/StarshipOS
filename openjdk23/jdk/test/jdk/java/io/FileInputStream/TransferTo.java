/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run main TransferTo
 * @bug 8272297
 * @summary Test FileInputStream.transferTo(FileOutputStream)
 * @key randomness
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import jdk.test.lib.RandomFactory;

public class TransferTo {
    private static int MIN_SIZE      = 10_000;
    private static int MAX_SIZE_INCR = 100_000_000 - MIN_SIZE;

    private static int ITERATIONS = 10;

    private static final Random RND = RandomFactory.getRandom();

    public static void main(String[] args) throws IOException {
        File dir = new File(".");
        File in = File.createTempFile("src", ".dat", dir);
        in.deleteOnExit();

        int length = MIN_SIZE + RND.nextInt(MAX_SIZE_INCR);
        byte[] bytes = new byte[length];

        try (RandomAccessFile rafi = new RandomAccessFile(in, "rw")) {
            rafi.write(bytes);
        }

        File out = File.createTempFile("dst", ".dat", dir);
        out.deleteOnExit();

        for (int i = 0; i < ITERATIONS; i++) {
            int posIn = RND.nextInt(length);
            int posOut = RND.nextInt(MIN_SIZE);

            try (RandomAccessFile rafo = new RandomAccessFile(out, "rw")) {
                rafo.setLength(posOut);
            }

            test(in, posIn, out, posOut);

            out.delete();
            out = File.createTempFile("dst", ".dat", dir);
            out.deleteOnExit();
        }
    }

    private static void test(File in, int posIn, File out, int posOut)
        throws IOException {
        try (FileInputStream fis = new FileInputStream(in)) {
            try (FileChannel fci = fis.getChannel()) {
                fci.position(posIn);
                long size = fci.size();
                long length = size - posIn;
                int count = Math.toIntExact(length);

                try (FileOutputStream fos = new FileOutputStream(out)) {
                    try (FileChannel fco = fos.getChannel()) {
                        fco.position(posOut);

                        long transferred = fis.transferTo(fos);
                        if (transferred != count)
                            fail(posIn, size, posOut,
                                "Transferred " + transferred +
                                ", expected " + count);

                        if (fci.position() != posIn + count)
                            fail(posIn, size, posOut,
                                "Input position " + fci.position() +
                                ", expected " + posIn + count);

                        if (fco.position() != posOut + count)
                            fail(posIn, size, posOut,
                                "Output position " + fco.position() +
                                ", expected " + posOut + count);

                        byte[] bytesIn = Files.readAllBytes(in.toPath());
                        byte[] bytesOut = Files.readAllBytes(out.toPath());
                        if (!Arrays.equals(bytesIn, posIn, posIn + count,
                                           bytesOut, posOut, posOut + count))
                            fail(posIn, size, posOut, "Contents unequal");
                    }
                }
            }
        }
    }

    private static void fail(int posIn, long size, int posOut,
                             String msg) {
        System.out.printf("Failure for posIn %s, size %d, posOut %d%n",
                          posIn, size, posOut);
        throw new RuntimeException(msg);
    }
}
