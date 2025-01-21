/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import jdk.test.lib.util.FileUtils;
import static java.nio.file.StandardCopyOption.*;

public class Common {

    static void copyFile (String src, String dst) {
        copyFile (new File(src), new File(dst));
    }

    static void copyDir (String src, String dst) {
        copyDir (new File(src), new File(dst));
    }

    static void copyFile (File src, File dst) {
        try {
            if (!src.isFile()) {
                throw new RuntimeException ("File not found: " + src.toString());
            }
            Files.copy(src.toPath(), dst.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    static void rm_minus_rf (File path) throws IOException, InterruptedException {
        if (!path.exists())
            return;
        FileUtils.deleteFileTreeWithRetry(path.toPath());
    }

    static void copyDir (File src, File dst) {
        if (!src.isDirectory()) {
            throw new RuntimeException ("Dir not found: " + src.toString());
        }
        if (dst.exists()) {
            throw new RuntimeException ("Dir exists: " + dst.toString());
        }
        dst.mkdir();
        String[] names = src.list();
        File[] files = src.listFiles();
        for (int i=0; i<files.length; i++) {
            String f = names[i];
            if (files[i].isDirectory()) {
                copyDir (files[i], new File (dst, f));
            } else {
                copyFile (new File (src, f), new File (dst, f));
            }
        }
    }

    /* expect is true if you expect to find it, false if you expect not to */
    static Class loadClass (String name, URLClassLoader loader, boolean expect){
        try {
            Class clazz = Class.forName (name, true, loader);
            if (!expect) {
                throw new RuntimeException ("loadClass: "+name+" unexpected");
            }
            return clazz;
        } catch (ClassNotFoundException e) {
            if (expect) {
                throw new RuntimeException ("loadClass: " +name + " not found");
            }
        }
        return null;
    }
}
