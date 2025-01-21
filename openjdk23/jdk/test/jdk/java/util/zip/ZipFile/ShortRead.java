/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/**
 * @test
 * @bug 6285124
 * @summary Small compressed zip entries should be read in one read() operation
 * @author Martin Buchholz
 */

import java.io.*;
import java.util.zip.*;

public class ShortRead {

    public static void main(String[] args) throws Exception {
        final File zFile = new File("abc.zip");
        try {
            final String entryName = "abc";
            final String data = "Data disponible";
            try (FileOutputStream fos = new FileOutputStream(zFile);
                 ZipOutputStream zos = new ZipOutputStream(fos))
            {
                zos.putNextEntry(new ZipEntry(entryName));
                zos.write(data.getBytes("ASCII"));
                zos.closeEntry();
            }

            try (ZipFile zipFile = new ZipFile(zFile)) {
                final ZipEntry zentry = zipFile.getEntry(entryName);
                final InputStream inputStream = zipFile.getInputStream(zentry);
                System.out.printf("size=%d csize=%d available=%d%n",
                                  zentry.getSize(),
                                  zentry.getCompressedSize(),
                                  inputStream.available());
                byte[] buf = new byte[data.length()];
                final int count = inputStream.read(buf);
                if (! new String(buf, "ASCII").equals(data) ||
                    count != data.length())
                    throw new Exception("short read?");
            }
        } finally {
            zFile.delete();
        }
    }
}
