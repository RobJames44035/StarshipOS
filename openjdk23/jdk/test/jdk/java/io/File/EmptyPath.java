/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
   @bug 4842706
   @summary Test some file operations with empty path
 */

import java.io.*;

public class EmptyPath {
    public static void main(String [] args) throws Exception {
        File f = new File("");
        f.mkdir();
        try {
            f.createNewFile();
            throw new RuntimeException("Expected exception not thrown");
        } catch (IOException ioe) {
            // Correct result
        }
        try {
            FileInputStream fis = new FileInputStream(f);
            fis.close();
            throw new RuntimeException("Expected exception not thrown");
        } catch (FileNotFoundException fnfe) {
            // Correct result
        }
    }
}
