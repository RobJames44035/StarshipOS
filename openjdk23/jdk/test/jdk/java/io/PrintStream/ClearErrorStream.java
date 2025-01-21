/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/**
 * @test
 * @bug  4491255
 * @summary Test for a new protected method PrintStream.clearError()
 *      to reset the internal error state
 */

import java.io.*;

public class ClearErrorStream extends PrintStream {

   public ClearErrorStream(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
   }

    public static void main(String[] args) throws Exception {

        File f = new File(System.getProperty("test.dir", "."),
                          "print-stream.out");
        f.deleteOnExit();

        ClearErrorStream out = new ClearErrorStream(
                                new BufferedOutputStream(
                                new FileOutputStream(f)),
                                true);
        out.println("Hello World!");
        out.close();
        out.println("Writing after close");

        if (out.checkError()) {
            System.out.println("An error occured");
            out.clearError();

            if (!out.checkError()) {
                System.out.println("Error status cleared");
            } else {
                throw new Exception("Error Status unchanged");
            }
         }
         else {
             System.out.println(" No error occured");
         }
    }
}
