/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
   @bug 4192018
   @summary URLConnection.getOutputStream() fails after connect()
   */
import java.net.*;
import java.io.*;

public class GetOutputStream {
    public static void main (String argv[]) {
        try {
            URL url = new URL("http://sunweb.ebay/");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            System.out.println("Passed!");
        } catch (Exception ex) {
            if (ex instanceof java.net.ProtocolException) {
                throw new RuntimeException("getOutputStream failure.");
            }
        }
    }
}
