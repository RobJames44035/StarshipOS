/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4639576
 * @summary java.net.URLConnection.guessContentTypeFromStream cannot
 * handle Exif format
 */

import java.net.URLConnection;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * This test makes sure that URLConnection.guessContentTypeFromStream
 * recognizes Exif file format. This test uses the file:
 * olympus.jpg for testing the same.
 */

public class ExifContentGuesser {

    public static void main(String args[]) throws Exception {
        String filename = System.getProperty("test.src", ".") +
                          "/" + "olympus.jpg";
        System.out.println("filename: " + filename);
        InputStream in = null;

        try {
            in = new BufferedInputStream(new FileInputStream(filename));

            String content_type = URLConnection.guessContentTypeFromStream(in);
            if (content_type == null) {
                throw new Exception("Test failed: Could not recognise " +
                                "Exif image");
            } else {
                System.out.println("content-type: " + content_type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
