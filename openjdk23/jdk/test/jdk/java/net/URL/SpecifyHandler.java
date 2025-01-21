/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4135031
 * @summary Test new URL contructors that allow specification of a
 *          URLStreamHandler protocol handler.
 * @modules java.base/sun.net.www.protocol.file
 */
import java.net.*;

public class SpecifyHandler {

    public static void main(String args[]) throws Exception {
        URLStreamHandler handler = getFileHandler();
        URL url1 = new URL("file", "", -1, "/bogus/index.html", handler);
        URL url2 = new URL(null, "file://bogus.index.html", handler);
    }

    private static URLStreamHandler getFileHandler() throws Exception {
        Class c = Class.forName("sun.net.www.protocol.file.Handler");
        return (URLStreamHandler)c.newInstance();
    }
}
