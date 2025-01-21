/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4134855
   @summary Test for opening non existant file
 */

import java.net.*;
import java.io.*;

public class Connect {
    public static void main(String s[]) throws Exception {
            try {
                // This file does not exist.
                URL url = new URL("file:azwe.txt");
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                // We reach here in JDK1.2beta3.
                throw new RuntimeException("No FileNotFoundException thrown.");
            }
            catch(MalformedURLException e) {
            }
            catch(IOException e) {
            }
        }
}
