/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4273031
 * @summary ClassLoader.getResouce() should be able to
 *          find resources with leading "." in their names
 * @library /test/lib
 * @run main GetDotResource
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import jdk.test.lib.process.ProcessTools;

public class GetDotResource {

    private static final String JAR_FILENAME = "resource.jar";
    private static final String DOT_FILENAME = ".resource";
    private static final String CP = JAR_FILENAME + File.pathSeparator
            + System.getProperty("test.class.path");

    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            createJar(JAR_FILENAME, DOT_FILENAME);

            ProcessTools.executeTestJava("-cp", CP,
                                         GetDotResource.class.getName(),
                                         DOT_FILENAME)
                        .outputTo(System.out)
                        .errorTo(System.out)
                        .shouldHaveExitValue(0);
        } else {
            runTest(args[0]);
        }
    }

    public static void runTest(String dotFileName) throws Exception {
        if (GetDotResource.class.getClassLoader().
            getResourceAsStream(dotFileName) == null) {
            throw new Exception("Could not find resource with "
                                + "leading . in their names");
        }
    }

    public static void createJar(String jarFileName, String dotFileName)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(jarFileName);
             JarOutputStream out = new JarOutputStream(fos))
        {
            out.putNextEntry(new JarEntry(dotFileName));
            out.closeEntry();
        }
    }
}
