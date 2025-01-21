/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import com.sun.tools.javac.file.FSInfo;
import com.sun.tools.javac.util.Context;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/*
 * @test
 * @bug 8232170
 * @summary Test com.sun.tools.javac.file.FSInfo
 * @modules jdk.compiler/com.sun.tools.javac.util
 *          jdk.compiler/com.sun.tools.javac.file
 * @run testng FSInfoTest
 */
public class FSInfoTest {

    /**
     * Tests that if a jar file has a manifest with a invalid path value for {@code Class-Path} attribute,
     * then parsing such a jar file through {@link FSInfo#getJarClassPath(Path)} doesn't throw any other
     * exception other than {@link IOException}
     *
     * @throws Exception
     */
    @Test
    public void testInvalidClassPath() throws Exception {
        final String invalidOSPath = System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("windows")
                ? "C:\\*" : "foo\u0000bar";
        final Path jarFile = Files.createTempFile(null, ".jar");
        jarFile.toFile().deleteOnExit();
        final Manifest mf = new Manifest();
        mf.getMainAttributes().putValue("Manifest-Version", "1.0");
        // add Class-Path which points to an invalid path
        System.out.println("Intentionally using an invalid Class-Path entry " + invalidOSPath + " in manifest");
        mf.getMainAttributes().putValue("Class-Path", invalidOSPath + " " + "/some/other-random/path");

        // create a jar file with the manifest
        try (final JarOutputStream jar = new JarOutputStream(Files.newOutputStream(jarFile), mf)) {
        }
        final FSInfo fsInfo = FSInfo.instance(new Context());
        try {
            fsInfo.getJarClassPath(jarFile);
            // we don't rely on fsInfo.getJarClassPath to throw an exception for invalid
            // paths. Hence no Assert.fail(...) call here. But if it does throw some exception,
            // then that exception should always be a IOException.
        } catch (IOException ioe) {
            // expected
            System.out.println("(As expected) FSInfo.getJarClassPath threw an IOException - " + ioe.getMessage());
        }
    }
}
