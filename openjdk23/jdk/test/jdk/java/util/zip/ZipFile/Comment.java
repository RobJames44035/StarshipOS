/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
   @bug 4334846
   @summary Make sure zip file comments of various sizes can be written.
   @author Martin Buchholz
 */

import java.io.*;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Comment {
    private static final String entryName = "entryName";
    private static final String entryContents = "entryContents";
    private static final int commentMaxLength = 0xFFFF;
    private static final int [] commentLengths
        = { 0, 1, 32768, commentMaxLength - 1, commentMaxLength };

    public static void main(String argv[])
        throws Exception
    {
        for (int i = 0; i < commentLengths.length; ++i) {
            int commentLength = commentLengths[i];
            String comment = buildComment(commentLength);
            String name = "Test" + commentLength + ".zip";
            writeZipFile(name, comment);
            verifyZipFile(name, comment);
            new File(name).delete();
            System.out.println(commentLength + ": successful");
        }
    }

    private static void writeZipFile(String name, String comment)
        throws IOException
    {
        try (FileOutputStream fos = new FileOutputStream(name);
             ZipOutputStream zos = new ZipOutputStream(fos))
        {
            zos.setComment(comment);
            ZipEntry ze = new ZipEntry(entryName);
            ze.setMethod(ZipEntry.DEFLATED);
            zos.putNextEntry(ze);
            new DataOutputStream(zos).writeUTF(entryContents);
            zos.closeEntry();
        }
    }

    private static void verifyZipFile(String name, String comment)
        throws Exception
    {
        // Check that Zip entry was correctly written.
        try (ZipFile zipFile = new ZipFile(name)) {
            ZipEntry zipEntry = zipFile.getEntry(entryName);
            InputStream is = zipFile.getInputStream(zipEntry);
            String result = new DataInputStream(is).readUTF();
            if (!result.equals(entryContents))
                throw new Exception("Entry contents corrupted");
        }

        try (RandomAccessFile file = new RandomAccessFile(name, "r")) {
            // Check that comment length was correctly written.
            file.seek(file.length() - comment.length()
                      - ZipFile.ENDHDR + ZipFile.ENDCOM);
            int b1 = file.readUnsignedByte();
            int b2 = file.readUnsignedByte();
            if (b1 + (b2 << 8) != comment.length())
                throw new Exception("Zip file comment length corrupted");

            // Check that comment was correctly written.
            file.seek(file.length() - comment.length());
            byte [] bytes = new byte [comment.length()];
            file.readFully(bytes);
            if (! comment.equals(new String(bytes, "UTF8")))
                throw new Exception("Zip file comment corrupted");
        }
    }

    private static String buildComment(int length) {
        StringBuffer result = new StringBuffer();
        while (result.length() < length) {
            /* Endhdr is 22 bytes long, so this pattern makes it easy
             * to see if it is copied correctly */
            result.append("abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUV");
        }
        String string = result.toString();
        string = string.substring(string.length() - length);
        return string;
    }
}
