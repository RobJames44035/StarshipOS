/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/* @test
 * @bug 5086348
 * @summary URL.openConnection(Proxy.NO_PROXY) throws NULLPointerException
 * @run main/othervm OpenConnection
 */

import java.io.*;
import java.net.*;

public class OpenConnection {
    public static void main(String[] args) throws IOException {
        URL u = new URL("http://foo.bar.baz/");
        try {
            // Will throw NullPointerException if not fixed
            URLConnection con = u.openConnection(Proxy.NO_PROXY);
        } catch (UnknownHostException ex) {
            // That's OK, we were expecting that!
            return;
        }
    }
}
