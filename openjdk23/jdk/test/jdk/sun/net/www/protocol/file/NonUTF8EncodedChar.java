/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @bug 6522294
 * @summary If URI scheme is file and URL is not UTF-8 encoded, the ParseUtil.decode throws an Exception
 */

import java.net.*;
import java.io.*;

public class NonUTF8EncodedChar
{
    public static void main(String[] args) {
        try {
            String s = "file:///c:/temp//m%FCnchen.txt";
            System.out.println("URL = "+s);
            URL url = new URL(s);
            URLConnection urlCon = url.openConnection();
            System.out.println("succeeded");

            urlCon.getInputStream();
             System.out.println("succeeded");

        } catch (IOException ioe) {
            // Ignore this is ok. The file may not exist.
            ioe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            String message = iae.getMessage();
            if (message == null || message.equals("")) {
                System.out.println("No message");
                throw new RuntimeException("Failed: No message in Exception");
            }
        }
    }
}
