/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Stack trace should have module information
 * @run testng ModuleFrames
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ModuleFrames {

    @Test
    public void testModuleName() {
        try {
            Integer.parseInt("a");
        } catch (NumberFormatException e) {

            StackTraceElement[] stack = e.getStackTrace();
            StackTraceElement topFrame = stack[0];
            StackTraceElement thisFrame = findFrame(this.getClass().getName(), stack);

            assertEquals(topFrame.getModuleName(), "java.base",
                    "Expected top frame to be in module java.base");

            assertTrue(topFrame.toString().startsWith("java.base"),
                    "Expected toString of top frame to omit loader name, start with module name: java.base");

            assertTrue(!topFrame.toString().contains("@"),
                    "Expected toString of top frame not to include module version ('@')");

            Path home = Paths.get(System.getProperty("java.home"));
            boolean isImage = Files.exists(home.resolve("lib").resolve("modules"));
            if (isImage) {
                assertTrue(!topFrame.getModuleVersion().isEmpty(),
                        "Expected non-empty STE.getModuleVersion() for top frame");
            }

            assertNull(thisFrame.getModuleName(),
                    "Expected frame for test not to have a module name");

            assertTrue(thisFrame.toString().startsWith(this.getClass().getName()),
                    "Expected toString to start with class name (no loader or module name");

            ClassLoader testCL = this.getClass().getClassLoader();
            if (ClassLoader.getSystemClassLoader().getClass().isInstance(testCL)) {
                assertEquals(thisFrame.getClassLoaderName(), "app",
                        "Expect STE to have loader name of \"app\"");
            }
        }
    }

    private static StackTraceElement findFrame(String cn, StackTraceElement[] stack) {
        return Arrays.stream(stack)
                .filter(s -> s.getClassName().equals(cn))
                .findFirst()
                .get();
    }
}
