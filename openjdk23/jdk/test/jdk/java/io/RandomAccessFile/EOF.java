/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
   @bug 4017497
   @summary Check that read returns -1 on EOF, as specified
 */

import java.io.*;

public class EOF {

    public static void main(String[] args) throws IOException {
        byte buf[] = new byte[100];
        int n;
        String dir = System.getProperty("test.src", ".");
        RandomAccessFile raf = new RandomAccessFile(new File(dir, "EOF.java"), "r");
        try {
            for (;;) {
                n = raf.read(buf, 0, buf.length);
                if (n <= 0) break;
            }
            if (n != -1)
                throw new RuntimeException("Expected -1 for EOF, got " + n);
        } finally {
            raf.close();
        }
    }

}
