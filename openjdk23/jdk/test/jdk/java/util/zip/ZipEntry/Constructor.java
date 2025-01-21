/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4243169
   @summary Make sure ZipEntry static initializer will load the
            zip library.
   */

import java.util.zip.ZipEntry;
import java.util.jar.JarEntry;
import java.io.*;

public class Constructor {
    public static void main(String argv[]) throws Exception {
        ZipEntry e1 = new ZipEntry("foo");
        JarEntry e2 = new JarEntry("bar");
    }
}
