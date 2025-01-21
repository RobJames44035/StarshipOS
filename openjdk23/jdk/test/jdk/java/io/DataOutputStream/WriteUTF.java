/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4260284 8219196 8223254
 * @summary Test if DataOutputStream will overcount written field.
 * @requires (sun.arch.data.model == "64" & os.maxMemory >= 4g)
 * @run testng/othervm -Xmx4g WriteUTF
 */

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;

import org.testng.annotations.Test;

public class WriteUTF {
    @Test
    public static void overcountWrittenField() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF("Hello, World!");  // 15
        dos.flush();
        if  (baos.size() != dos.size())
            throw new RuntimeException("Miscounted bytes in DataOutputStream.");
    }

    private static void writeUTF(int size) throws IOException {
        // this character gives 3 bytes when encoded using UTF-8
        String s = "\u0800".repeat(size);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF(s);
    }

    @Test(expectedExceptions = UTFDataFormatException.class)
    public void utfDataFormatException() throws IOException {
        writeUTF(1 << 16);
    }

    // Without 8219196 fix, throws ArrayIndexOutOfBoundsException instead of
    // expected UTFDataFormatException. Requires 4GB of heap (-Xmx4g) to run
    // without throwing an OutOfMemoryError.
    @Test(expectedExceptions = UTFDataFormatException.class)
    public void arrayIndexOutOfBoundsException() throws IOException {
        writeUTF(Integer.MAX_VALUE / 3 + 1);
    }
}
