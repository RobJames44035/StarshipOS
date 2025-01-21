/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/* @test
 * @bug 8038491
 * @summary Crash in ZipFile.read() when ZipFileInputStream is shared between threads
 * @library /test/lib
 * @build jdk.test.lib.Platform
 *        jdk.test.lib.util.FileUtils
 * @run main MultiThreadedReadTest
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import jdk.test.lib.util.FileUtils;

public class MultiThreadedReadTest extends Thread {

    private static final int NUM_THREADS = 10;
    private static final String ZIPFILE_NAME =
        System.currentTimeMillis() + "-bug8038491-tmp.large.zip";
    private static final String ZIPENTRY_NAME = "random.txt";
    private static InputStream is = null;

    public static void main(String args[]) throws Exception {
        createZipFile();
        try (ZipFile zf = new ZipFile(new File(ZIPFILE_NAME))) {
            is = zf.getInputStream(zf.getEntry(ZIPENTRY_NAME));
            Thread[] threadArray = new Thread[NUM_THREADS];
            for (int i = 0; i < threadArray.length; i++) {
                threadArray[i] = new MultiThreadedReadTest();
            }
            for (int i = 0; i < threadArray.length; i++) {
                threadArray[i].start();
            }
            for (int i = 0; i < threadArray.length; i++) {
                threadArray[i].join();
            }
        } finally {
            long t = System.currentTimeMillis();
            FileUtils.deleteFileIfExistsWithRetry(Paths.get(ZIPFILE_NAME));
            System.out.println("Deleting zip file took:" +
                    (System.currentTimeMillis() - t) + "ms");
        }
    }

    private static void createZipFile() throws Exception {
        CRC32 crc32 = new CRC32();
        long t = System.currentTimeMillis();
        File zipFile = new File(ZIPFILE_NAME);
        try (FileOutputStream fos = new FileOutputStream(zipFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ZipOutputStream zos = new ZipOutputStream(bos)) {
            ZipEntry e = new ZipEntry(ZIPENTRY_NAME);
            e.setMethod(ZipEntry.STORED);
            byte[] toWrite = "BLAH".repeat(10_000).getBytes();
            e.setTime(t);
            e.setSize(toWrite.length);
            crc32.reset();
            crc32.update(toWrite);
            e.setCrc(crc32.getValue());
            zos.putNextEntry(e);
            zos.write(toWrite);
        }
    }

    @Override
    public void run() {
        try {
            while (is.read() != -1) { }
        } catch (Exception e) {
            System.out.println("read exception:" + e);
            // Swallow any Exceptions (which are expected) - we're only interested in the crash
        }
    }
}
