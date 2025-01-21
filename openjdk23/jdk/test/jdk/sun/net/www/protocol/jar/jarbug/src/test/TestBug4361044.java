/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/*
 * ResourceBundle from jar not found if jar exists in path
 * which has symbol !
 *
 * 3 votes
 *
 * Note: Execute "real" test in separate vm instance so that any locks
 * held on files will be released when this separate vm exits and the
 * invoking vm can clean up if necessary.
 */
public class TestBug4361044 extends JarTest
{
    public void run(String[] args) throws Exception {
        File tmp = createTempDir();
        try {
            File dir = new File(tmp, "dir!name");
            dir.mkdir();
            File testFile = copyResource(dir, "jar1.jar");
            URL[] urls = new URL[1];
            urls[0] = new URL("jar:" + testFile.toURI().toURL() + "!/");
            URLClassLoader loader = new URLClassLoader(urls);
            loader.loadClass("jar1.LoadResourceBundle").newInstance();
        } finally {
            deleteRecursively(tmp);
        }
    }

    public static void main(String[] args) throws Exception {
        new TestBug4361044().run(args);
    }
}
