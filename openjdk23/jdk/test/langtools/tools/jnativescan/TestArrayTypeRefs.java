/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib .. ./cases/modules
 * @build JNativeScanTestBase
 *     cases.classpath.arrayref.App
 * @run junit TestArrayTypeRefs
 */

import jdk.test.lib.util.JarUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

public class TestArrayTypeRefs extends JNativeScanTestBase {

    static Path ARRAY_REF;

    @BeforeAll
    public static void before() throws IOException {
        ARRAY_REF = Path.of("arrayref.jar");
        Path testClasses = Path.of(System.getProperty("test.classes", ""));
        JarUtils.createJarFile(ARRAY_REF, testClasses, Path.of("arrayref", "App.class"));
    }

    @Test
    public void testSingleJarClassPath() {
        assertSuccess(jnativescan("--class-path", ARRAY_REF.toString()))
                .stderrShouldBeEmpty()
                .stdoutShouldContain("<no restricted methods>");
    }
}
