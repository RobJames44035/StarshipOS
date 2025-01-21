/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug     6551367
 * @summary javadoc throws ClassCastException when an link tag tries to reference constructor.
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @run main T6551367 T6551367.java
 */

/*
 * ***NOTE: This is not a tool test!, should be moved to doclets.***
 */
import java.io.File;

import jdk.javadoc.doclet.DocletEnvironment;

public class T6551367 {
    public T6551367() {}
    public boolean run(DocletEnvironment root) {
        return true;
    }
    /** Here, in the javadoc for this method, I try to link to
     *  {@link #<init> a constructor}.
     */
    public static void main(String... args) {
        File testSrc = new File(System.getProperty("test.src", "."));
        File destDir = new File(System.getProperty("user.dir", "."));
        for (String file : args) {
            File source = new File(testSrc, file);
            String[] array = {
                "-Xdoclint:none",
                source.getPath(),
                "-d",
                destDir.getAbsolutePath()
            };

            int rc = jdk.javadoc.internal.tool.Main.execute(array);
            if (rc != 0)
                throw new Error("unexpected exit from javadoc: " + rc);
        }
    }
}
