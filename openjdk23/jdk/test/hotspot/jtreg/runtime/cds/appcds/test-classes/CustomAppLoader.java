/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


// This is a handy class for running an application inside a custom class loader. This
// is used for testing CDS handling of unregistered classes (i.e., archived classes loaded
// by custom class loaders).
//
// See test/hotspot/jtreg/runtime/cds/appcds/loaderConstraints/LoaderConstraintsTest.java
// for an example.
public class CustomAppLoader {
    // args[0] = App JAR file
    // args[1] = App main class
    // args[2...] = arguments for the main class
    public static void main(String args[]) throws Throwable {
        File f = new File(args[0]);
        URL[] classLoaderUrls = new URL[] {f.getAbsoluteFile().toURI().toURL()};
        URLClassLoader loader = new URLClassLoader(classLoaderUrls, CustomAppLoader.class.getClassLoader());
        Class k = Class.forName(args[1], true, loader);
        Class parameterTypes[] = new Class[] {String[].class};
        Method mainMethod = k.getDeclaredMethod("main", parameterTypes);
        String appArgs[] = new String[args.length - 2];
        Object invokeArgs[] = new Object[] {appArgs};
        for (int i = 0; i < appArgs.length; i++) {
            appArgs[i] = args[i + 2];
        }
        mainMethod.invoke(null, invokeArgs);
    }
}
