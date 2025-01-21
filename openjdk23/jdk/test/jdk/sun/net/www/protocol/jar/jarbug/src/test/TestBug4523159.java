/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
 * Issuing a getResourceAsStream() call will throw an exception if:
 *  a) classes and resources are inside a file, and
 *  b) the jar file is located in a directory containing an exclaimation
 *     mark ("!"), like "C:\Java!".
 *
 * 15 votes.
 *
 * Note: Execute "real" test in separate vm instance so that any locks
 * held on files will be released when this separate vm exits and the
 * invoking vm can clean up if necessary.
 */
public class TestBug4523159 extends JarTest
{
    public void run(String[] args) throws Exception {
        File tmp = createTempDir();
        try {
            File dir = new File(tmp, "dir!name");
            dir.mkdir();
            File testFile = copyResource(dir, "jar1.jar");

            // Case 1: direct access
            URL url = new URL("jar:" + testFile.toURI().toURL() + "!/res1.txt");
            JarURLConnection conn = (JarURLConnection) url.openConnection();
            JarFile file = conn.getJarFile();
            JarEntry entry = conn.getJarEntry();
            byte[] buffer = readFully(file.getInputStream(entry));
            String str = new String(buffer);
            if (!str.equals("This is jar 1\n")) {
                throw (new Exception("resource content invalid"));
            }

            // Case 2: indirect access
            URL[] urls = new URL[1];
            urls[0] = new URL("jar:" + testFile.toURI().toURL()  + "!/");
            URLClassLoader loader = new URLClassLoader(urls);
            loader.loadClass("jar1.GetResource").newInstance();
        } finally {
            deleteRecursively(tmp);
        }
    }

    public static void main(String[] args) throws Exception {
        new TestBug4523159().run(args);
    }
}
