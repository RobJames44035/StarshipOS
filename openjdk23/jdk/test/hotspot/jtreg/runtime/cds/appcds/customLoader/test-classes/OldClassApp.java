/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import jdk.test.whitebox.WhiteBox;

public class OldClassApp {
    // Prevent the classes from being GC'ed too soon.
    static HashMap<String, Class> clsMap = new HashMap<>();
    public static void main(String args[]) throws Exception {
        String path = args[0];
        URL url = new File(path).toURI().toURL();
        URL[] urls = new URL[] {url};
        System.out.println(path);
        System.out.println(url);

        boolean inArchive = false;
        if (args[1].equals("true")) {
            inArchive = true;
        } else if (args[1].equals("false")) {
            inArchive = false;
        } else {
            throw new RuntimeException("args[1] can only be either \"true\" or \"false\", actual " + args[1]);
        }

        // The OldClassAndInf.java test under appcds/dynamicArchive passes the keep-alive
        // argument for preventing the classes from being GC'ed prior to dumping of
        // the dynamic CDS archive.
        int startIdx = 2;
        boolean keepAlive = false;
        if (args[2].equals("keep-alive")) {
            keepAlive = true;
            startIdx = 3;
        }

        URLClassLoader urlClassLoader =
            new URLClassLoader("OldClassAppClassLoader", urls, null);

        for (int i = startIdx; i < args.length; i++) {
            Class c = urlClassLoader.loadClass(args[i]);
            System.out.println(c);
            System.out.println(c.getClassLoader());
            if (keepAlive) {
                clsMap.put(args[i], c);
            }

            // [1] Check that class is defined by the correct loader
            if (c.getClassLoader() != urlClassLoader) {
                throw new RuntimeException("c.getClassLoader() == " + c.getClassLoader() +
                                           ", expected == " + urlClassLoader);
            }

            // [2] Check that class is loaded from shared static archive.
            if (inArchive) {
                WhiteBox wb = WhiteBox.getWhiteBox();
                if (wb.isSharedClass(OldClassApp.class)) {
                    if (!wb.isSharedClass(c)) {
                        throw new RuntimeException("wb.isSharedClass(c) should be true");
                    }
                }
            }
        }
    }
}
