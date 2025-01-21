/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/* @test
 * @bug 8054029 8313368
 * @requires (os.family == "linux")
 * @summary FileChannel.size() should be equal to RandomAccessFile.size() and > 0 for block devs on Linux
 * @library /test/lib
 */

import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.channels.FileChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

import jtreg.SkippedException;

public class BlockDeviceSize {
    private static final List<String> BLK_FNAMES = List.of("/dev/sda1", "/dev/nvme0n1", "/dev/xvda1") ;

    public static void main(String[] args) throws Throwable {
        for (String blkFname: BLK_FNAMES) {
            Path blkPath = Path.of(blkFname);
            try (FileChannel ch = FileChannel.open(blkPath, READ);
                 RandomAccessFile file = new RandomAccessFile(blkFname, "r")) {

                long size1 = ch.size();
                long size2 = file.length();
                if (size1 != size2) {
                    throw new RuntimeException("size differs when retrieved" +
                            " in different ways: " + size1 + " != " + size2);
                }
                if (size1 <= 0) {
                    throw new RuntimeException("size() for a block device size returns zero or a negative value");
                }
                System.out.println("OK");

            } catch (NoSuchFileException nsfe) {
                System.err.println("File " + blkFname + " not found." +
                        " Skipping test");
            } catch (AccessDeniedException ade) {
                throw new SkippedException("Access to " + blkFname + " is denied."
                        + " Run test as root.", ade);
            }

        }
    }
}
