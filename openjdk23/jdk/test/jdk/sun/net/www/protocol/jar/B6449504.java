/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6449504
 * @run main/othervm B6449504 caching
 * @run main/othervm B6449504 no_caching
 * @summary REGRESSION: ZipException throws when try to read a XML file
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class B6449504 {

    public static void main (String[] args) throws Exception {
        setup();
        boolean caching = args[0].equals("caching");
        String dirname = System.getProperty("test.classes");
        File f = new File(dirname);
        dirname = f.toURI().toString();

        String u = "jar:" + dirname + "/bar.jar";
        URL url = new URL(u + "!/DoesNotExist.txt");
        System.out.println("url = " + url);
        JarURLConnection j1 = (JarURLConnection)url.openConnection();

        URL url2 = new URL(u + "!/test.txt");
        System.out.println("url2 = " + url2);
        JarURLConnection j2 = (JarURLConnection)url2.openConnection();

        j1.setUseCaches(caching);
        j2.setUseCaches(caching);

        /* connecting to j2 opens the jar file but does not read it */

        j2.connect();

        try {
            /* attempt to read a non-existing entry in the jar file
             * shows the bug, where the jar file is closed after the
             * attempt fails.
             */
            InputStream is = j1.getInputStream();
        } catch (IOException e) {
            System.out.println("Got expected exception from j1 ");
        }

        /* If bug present, this will fail because we think the jar
         * is ready to be read, after the connect() above, but we
         * get a ZipException because it has been closed
         */
        InputStream is = j2.getInputStream();
        readAndClose(is);
        System.out.println("OK");
    }

    static void readAndClose(InputStream is) throws IOException {
        while (is.read() != -1) ;
        is.close();
    }

    static void setup() throws IOException {
        Files.copy(Paths.get(System.getProperty("test.src"), "bar.jar"),
                   Paths.get(System.getProperty("test.classes"), "bar.jar"),
                   StandardCopyOption.REPLACE_EXISTING);
    }
}
