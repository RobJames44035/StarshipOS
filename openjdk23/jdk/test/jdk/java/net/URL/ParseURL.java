/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4255229
 * @summary URLStreamHandler.parseURL error
 *
 */
import java.io.*;
import java.net.*;

public class ParseURL {
  public static void main(String args[]) throws MalformedURLException {
    String url = new URL(new URL("http://cnn.com"), "index.html").toExternalForm();
    if (url.equalsIgnoreCase("http://cnn.com/index.html")) {
      System.err.println("Success!!");
    } else {
      throw new RuntimeException("The result is "+url+", it should be http://cnn.com/index.html");
    }
  }
}
