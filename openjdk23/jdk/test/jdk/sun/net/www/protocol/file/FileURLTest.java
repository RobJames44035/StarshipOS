/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4474391
 * @summary url: file:///D|/Projects/tmp/test.html: urlConnection.getInputStream() broken.
 */
import java.io.*;
import java.net.*;

public class FileURLTest {

    public static void main(String [] args)
    {
        String name = System.getProperty("os.name");
        if (name.startsWith("Windows")) {
            String urlStr = "file:///C|/nonexisted.txt";

            try {
                URL url = new URL(urlStr);
                URLConnection urlConnection = url.openConnection();
                InputStream in = urlConnection.getInputStream();
                in.close();
            } catch (IOException e) {
                if (e.getMessage().startsWith("C:\\nonexisted.txt")) {
                    System.out.println("Test passed!");
                } else {
                    throw new RuntimeException("Can't handle '|' in place of ':' in file urls");
                }
            }
        }
    }
}
