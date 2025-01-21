/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.*;
import java.net.*;
import jdk.test.whitebox.WhiteBox;

public class DuplicatedCustomApp {
    static WhiteBox wb = WhiteBox.getWhiteBox();
    static URLClassLoader loaders[];

    // If DuplicatedCustomApp.class is loaded from JAR file, it means we are dumping the
    // dynamic archive.
    static boolean is_dynamic_dumping = !wb.isSharedClass(DuplicatedCustomApp.class);
    static boolean is_running_with_dynamic_archive = !is_dynamic_dumping;

    public static void main(String args[]) throws Exception {
        String path = args[0];
        URL url = new File(path).toURI().toURL();
        URL[] urls = new URL[] {url};
        System.out.println(path);
        System.out.println(url);

        int num_loops = 1;
        if (args.length > 1) {
            num_loops = Integer.parseInt(args[1]);
        }
        loaders = new URLClassLoader[num_loops];
        for (int i = 0; i < num_loops; i++) {
            loaders[i] = new URLClassLoader(urls);
        }

        if (is_dynamic_dumping) {
            // Try to load the super interfaces of CustomLoadee2 in different orders
            for (int i = 0; i < num_loops; i++) {
                int a = (i + 1) % num_loops;
                loaders[a].loadClass("CustomInterface2_ia");
            }
            for (int i = 0; i < num_loops; i++) {
                int a = (i + 2) % num_loops;
                loaders[a].loadClass("CustomInterface2_ib");
            }
        }

        for (int i = 0; i < num_loops; i++) {
            System.out.println("============================ LOOP = " + i);
            URLClassLoader urlClassLoader = loaders[i];
            test(i, urlClassLoader, "CustomLoadee");
            test(i, urlClassLoader, "CustomInterface2_ia");
            test(i, urlClassLoader, "CustomInterface2_ib");
            test(i, urlClassLoader, "CustomLoadee2");
            test(i, urlClassLoader, "CustomLoadee3");
            test(i, urlClassLoader, "CustomLoadee3Child");
        }
    }

    private static void test(int i, URLClassLoader urlClassLoader, String name) throws Exception {
        Class c = urlClassLoader.loadClass(name);
        try {
            c.newInstance(); // make sure the class is linked so it can be archived
        } catch (Throwable t) {}
        boolean is_shared = wb.isSharedClass(c);

        System.out.println("Class = " + c + ", loaded from " + (is_shared ? "CDS" : "Jar"));
        System.out.println("Loader = " + c.getClassLoader());

        // [1] Check that the loaded class is defined by the correct loader
        if (c.getClassLoader() != urlClassLoader) {
            throw new RuntimeException("c.getClassLoader() == " + c.getClassLoader() +
                                       ", expected == " + urlClassLoader);
        }

        if (is_running_with_dynamic_archive) {
            // There's only one copy of the shared class of <name> in the
            // CDS archive.
            if (i == 0) {
                // The first time we must be able to load it from CDS.
                if (!is_shared) {
                    throw new RuntimeException("Must be loaded from CDS");
                }
            } else {
                // All subsequent times, we must load this from JAR file.
                if (is_shared) {
                    throw new RuntimeException("Must be loaded from JAR");
                }
            }
        }
    }
}
