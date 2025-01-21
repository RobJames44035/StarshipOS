/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4332284
 * @summary URL.getPort() should not return -1
 *
 */
import java.io.*;
import java.net.*;

public class GetDefaultPort {
    public static void main(String args[]) throws Exception {
        int p;
        URL url = new URL ("http://www.sun.com");

        if ((p=url.getDefaultPort ()) != 80)
            throw new Exception ("getDefaultPort returned wrong value: "+p);
    }
}
