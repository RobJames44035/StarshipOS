/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4871490
 * @summary URLClassLoader cannot load class which is in added path
 */

import java.net.*;
import java.io.*;

public class FileMap {
    public static void main(String[] args) {
        try {
            File f = File.createTempFile("test", null);
            f.deleteOnExit();
            String s = f.getAbsolutePath();
            s = s.startsWith("/") ? s : "/" + s;
            URL url = new URL("file://localhost"+s);
            InputStream in = url.openStream();
            in.close();
            url = new URL("file://LocalHost" + s);
            in = url.openStream();
            in.close();
        } catch (java.io.FileNotFoundException fnfe) {
            throw new RuntimeException("failed to recognize localhost");
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected exception: " + ex);
        }
    }
}
