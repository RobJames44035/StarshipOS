/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8187898
 * @summary Test of writeBytes(byte[])
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class WriteBytes {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream out = new BufferedOutputStream(baos, 512);
        PrintStream ps = new PrintStream(out, false);

        byte[] buf = new byte[128];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte)i;
        }

        ps.writeBytes(buf);
        assertTrue(baos.size() == 0, "Buffer should not have been flushed");
        ps.close();
        assertTrue(baos.size() == buf.length, "Stream size " + baos.size() +
            " but expected " + buf.length);

        ps = new PrintStream(out, true);
        ps.writeBytes(buf);
        assertTrue(baos.size() == 2*buf.length, "Stream size " + baos.size() +
            " but expected " + 2*buf.length);

        byte[] arr = baos.toByteArray();
        assertTrue(arr.length == 2*buf.length, "Array length " + arr.length +
            " but expected " + 2*buf.length);
        assertTrue(Arrays.equals(buf, 0, buf.length, arr, 0, buf.length),
            "First write not equal");
        assertTrue(Arrays.equals(buf, 0, buf.length, arr, buf.length,
            2*buf.length), "Second write not equal");

        ps.close();
        ps.writeBytes(buf);
        assertTrue(ps.checkError(), "Error condition should be true");
    }

    private static void assertTrue(boolean condition, String msg) {
        if (!condition)
            throw new RuntimeException(msg);
    }
}
