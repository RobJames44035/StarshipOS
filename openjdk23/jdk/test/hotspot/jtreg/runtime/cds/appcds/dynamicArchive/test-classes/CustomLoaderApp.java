/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class CustomLoaderApp {
    private static String className = "LambHello";
    // Prevent the class from being GC'ed too soon.
    private static Class keptC = null;

    public static void main(String args[]) throws Exception {
        String path = args[0];
        URL url = new File(path).toURI().toURL();
        URL[] urls = new URL[] {url};
        System.out.println(path);
        System.out.println(url);

        boolean init = false;
        if (args.length >= 2 && args[1].equals("init")) {
            init = true;
        }

        // The dynamicArchive/LambdaCustomLoader.java test passes the keep-alive
        // argument for preventing the class from being GC'ed prior to dumping of
        // the dynamic CDS archive.
        boolean keepAlive = false;
        if (args[args.length - 1].equals("keep-alive")) {
            keepAlive = true;
        }

        URLClassLoader urlClassLoader =
            new URLClassLoader("HelloClassLoader", urls, null);
        Class c = Class.forName(className, init, urlClassLoader);
        if (keepAlive) {
            keptC = c;
        }

        System.out.println(c);
        System.out.println(c.getClassLoader());
        if (c.getClassLoader() != urlClassLoader) {
            throw new RuntimeException("c.getClassLoader() == " + c.getClassLoader() +
                                       ", expected == " + urlClassLoader);
        }

        if (init) {
            Object o = c.newInstance();
            Method method = c.getMethod("main", String[].class);
            String[] params = null;
            method.invoke(null, (Object)params);
        }
    }
}
