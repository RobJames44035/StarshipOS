/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4480308
 * @summary URL is not throwinng exception for wrong URL format.
 *
 */

import java.net.*;

public class ParseAuthority {
    public static void main(String args[]) throws Exception {
        try {
            URL u1 = new URL("http://[fe80::]9999/path1/path2/");
            throw new RuntimeException("URL parser didn't catch" +
                                       " invalid authority field");
        } catch (MalformedURLException me) {
            if (!me.getMessage().startsWith("Invalid authority field")) {
                throw new RuntimeException("URL parser didn't catch" +
                                       " invalid authority field");
            }
        }

        try {
            URL u2 = new URL("http://[www.sun.com]:9999/path1/path2/");
            throw new RuntimeException("URL parser didn't catch" +
                                       " invalid host");
        } catch (MalformedURLException me) {
            if (!me.getMessage().startsWith("Invalid host")) {
                throw new RuntimeException("URL parser didn't catch" +
                                       " invalid host");
            }
        }
    }
}
