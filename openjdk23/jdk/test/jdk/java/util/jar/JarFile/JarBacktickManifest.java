/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8186334
 * @summary Make sure scanning manifest doesn't throw AIOOBE on certain strings containing backticks.
 * @library /test/lib/
 * @build jdk.test.lib.util.JarBuilder
 * @run testng JarBacktickManifest
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.jar.JarFile;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import jdk.test.lib.util.JarBuilder;

public class JarBacktickManifest {

    public static final String VERIFY_MANIFEST_JAR = "verifyManifest.jar";

    @BeforeClass
    public void initialize() throws Exception {
        JarBuilder jb = new JarBuilder(VERIFY_MANIFEST_JAR);
        jb.addAttribute("Test", " Class-`Path` ");
        jb.addAttribute("Test2", " Multi-`Release ");
        jb.build();
    }

    @Test
    public void test() throws Exception {
        try (JarFile jf = new JarFile(VERIFY_MANIFEST_JAR)) {  // do not set runtime versioning
            Assert.assertFalse(jf.isMultiRelease(), "Shouldn't be multi-release");
        }
    }

    @AfterClass
    public void close() throws IOException {
        Files.delete(new File(VERIFY_MANIFEST_JAR).toPath());
    }
}
