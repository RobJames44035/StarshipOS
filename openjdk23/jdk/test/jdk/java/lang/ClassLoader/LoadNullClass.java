/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4263588 4292814
 * @summary ClassLoader.loadClass() should not core dump
 *          on null class names.
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


public class LoadNullClass {
    public static void main(String[] args) throws Exception {
        File f = new File(System.getProperty("test.src", "."));
        // give the class loader a good but useless url
        FileClassLoader cl = new FileClassLoader
            (new URL[]{new URL("file:"+ f.getAbsolutePath())});
        cl.testFindLoadedClass(null);
    }
}

class FileClassLoader extends URLClassLoader {

    public FileClassLoader(URL[] urls) {
        super(urls);
    }

    public void testFindLoadedClass(String name) throws Exception {
        super.findLoadedClass(name);
    }
}
