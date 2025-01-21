/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4092350
 * @summary Attempting to open a stream on a directory should fail on
 *       all platforms.
 *
 */

import java.io.*;

public class OpenDir {

    public static void main(String args[]) throws Exception {
        // Perform the same test for FileInputStream, FileOutputStream
        // and RandomAccessFile.
        FileInputStream fs = null;
        // This test attempts to FileInputStream.open(".")
        // This should fail since . is a directory.
        try {
            fs = new FileInputStream(".");
            throw new
                Exception("FileInputStream.open should not work on dirs");
        } catch (IOException e) {
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(".");
            throw new
                Exception("FileOutputStream.open should'nt work on dirs");
        } catch (IOException e) {
        }

        RandomAccessFile ras = null;
        try {
            ras = new RandomAccessFile(".","r");
            throw new
                Exception("RandomAccessFile.open should'nt work on dirs");
        } catch (IOException e) {
        }
    }


}
