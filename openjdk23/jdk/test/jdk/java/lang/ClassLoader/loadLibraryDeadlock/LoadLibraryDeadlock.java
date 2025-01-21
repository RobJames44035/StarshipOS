/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * LoadLibraryDeadlock class triggers the deadlock between the two
 * lock objects - ZipFile object and ClassLoader.loadedLibraryNames hashmap.
 * Thread #2 loads a signed jar which leads to acquiring the lock objects in
 * natural order (ZipFile then HashMap) - loading a signed jar may involve
 * Providers initialization. Providers may load native libraries.
 * Thread #1 acquires the locks in reverse order, first entering loadLibrary
 * called from Class1, then acquiring ZipFile during the search for a class
 * triggered from JNI.
 */
import java.lang.*;
import java.net.URISyntaxException;

public class LoadLibraryDeadlock {

    public static void main(String[] args) {
        System.out.println("LoadLibraryDeadlock test started");
        Thread t1 = new Thread() {
            public void run() {
                try {
                    // an instance of unsigned class that loads a native library
                    Class<?> c1 = Class.forName("Class1");
                    Object o = c1.newInstance();
                    System.out.println("Class1 loaded from " + getLocation(c1));
                } catch (ClassNotFoundException |
                         InstantiationException |
                         IllegalAccessException e) {
                    System.out.println("Class Class1 not found.");
                    throw new RuntimeException(e);
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                try {
                    // load a class from a signed jar, which locks the JarFile
                    Class<?> c2 = Class.forName("p.Class2");
                    System.out.println("Class2 loaded from " + getLocation(c2));
                } catch (ClassNotFoundException e) {
                    System.out.println("Class Class2 not found.");
                    throw new RuntimeException(e);
                }
            }
        };
        t2.start();
        t1.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getLocation(Class<?> c) {
        var pd = c.getProtectionDomain();
        var cs = pd != null ? pd.getCodeSource() : null;
        try {
            // same format as returned by TestLoadLibraryDeadlock::getLocation
            return cs != null ? cs.getLocation().toURI().getPath() : null;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
