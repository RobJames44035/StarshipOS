/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4291009
 * @summary URLConnection fails to find resources
 *          when given file:/dir/./subdir/ URL
 */
import java.io.*;
import java.net.*;

public class FileLoaderTest {
  public static void main (String args[]) throws Exception {
      File tempFile = File.createTempFile("foo", ".txt");
      tempFile.deleteOnExit();
      String basestr = tempFile.toURL().toString();
      basestr = basestr.substring(0, basestr.lastIndexOf("/")+1);
      URL url = new URL(basestr+"."+"/");

      ClassLoader cl = new URLClassLoader (new URL[] { url });
      if (cl.getResource (tempFile.getName()) == null) {
          throw new RuntimeException("Returned null instead of " +
                                     tempFile.toURL().toString());
      }
   }
}
