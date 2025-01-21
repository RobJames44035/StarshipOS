/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.net.MalformedURLException;
import java.net.URL;

public class Child {

    public static void main(String[] args) throws MalformedURLException {
        if (args.length != 1) {
            System.err.println("Usage: java Child <protocol>");
            return;
        }
        String protocol = args[0];
        URL url = new URL(protocol + "://");

        // toExternalForm should return the protocol string
        String s = url.toExternalForm();
        if (!s.equals(protocol)) {
            System.err.println("Expected url.toExternalForm to return "
                                       + protocol + ", but got: " + s);
            System.exit(1);
        }
    }
}
