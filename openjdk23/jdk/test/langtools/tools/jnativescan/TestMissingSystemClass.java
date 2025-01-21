/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib .. ./cases/modules
 * @build JNativeScanTestBase
 * @compile --release 20 cases/classpath/missingsystem/App.java
 * @run junit TestMissingSystemClass
 */

import jdk.test.lib.util.JarUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

public class TestMissingSystemClass extends JNativeScanTestBase {

    static Path MISSING_SYSTEM;

    @BeforeAll
    public static void before() throws IOException {
        MISSING_SYSTEM = Path.of("missingsystem.jar");
        Path testClasses = Path.of(System.getProperty("test.classes", ""));
        JarUtils.createJarFile(MISSING_SYSTEM, testClasses, Path.of("missingsystem", "App.class"));
    }

    @Test
    public void testSingleJarClassPath() {
        assertFailure(jnativescan("--class-path", MISSING_SYSTEM.toString(), "--release", "21"))
                .stdoutShouldBeEmpty()
                .stderrShouldContain("Error while processing method")
                .stderrShouldContain("missingsystem.App::main(String[])void")
                .stderrShouldContain("CAUSED BY:")
                .stderrShouldContain("System class can not be found")
                .stderrShouldContain("java.lang.Compiler");
    }
}
