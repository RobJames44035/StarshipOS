/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class VerifyObjArrayCloneTestApp {
    public static void main(String args[]) throws Exception {
        ClassLoader appLoader = VerifyObjArrayCloneTestApp.class.getClassLoader();
        if (args.length == 0) {
            // Load the test classes from the classpath
            doTest(appLoader);
        } else {
            File f = new File(args[0]);
            URL[] classLoaderUrls = new URL[] {f.getAbsoluteFile().toURI().toURL()};
            URLClassLoader customLoader = new URLClassLoader(classLoaderUrls, appLoader);
            doTest(customLoader);
        }
    }

    public static void doTest(ClassLoader loader) throws Exception {
        try {
            Class.forName("InvokeCloneValid", /*initialize=*/true, loader);
        }  catch (VerifyError e) {
            throw new RuntimeException("Unexpected VerifyError", e);
        }

        try {
            Class.forName("InvokeCloneInvalid", /*initialize=*/true, loader);
            throw new RuntimeException("VerifyError expected but not thrown");
        } catch (VerifyError e) {
            System.out.println("Expected: " + e);
        }
    }
}
