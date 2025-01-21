/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.spi.ToolProvider;
import java.util.zip.ZipEntry;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
 * @test
 * @bug 8302293
 * @summary verify that a JAR file creation through "jar --create" operation
 *          works fine if the JAR file name is less than 3 characters in length
 * @run junit JarCreateFileNameTest
 */
public class JarCreateFileNameTest {

    private static final ToolProvider JAR_TOOL = ToolProvider.findFirst("jar")
            .orElseThrow(() ->
                    new RuntimeException("jar tool not found")
            );

    /*
     * Launches "jar --create --file" with file names of varying lengths and verifies
     * that the JAR file was successfully created.
     */
    @ParameterizedTest
    @ValueSource(strings = {"abcd", "abc", "ab", "a", "d.jar", "ef.jar"})
    void testCreate(final String targetJarFileName) throws Exception {
        final Path cwd = Path.of(".");
        final Path tmpFile = Files.createTempFile(cwd, "8302293", ".txt");
        final String fileName = tmpFile.getFileName().toString();
        final int exitCode = JAR_TOOL.run(System.out, System.err,
                "--create", "--file", targetJarFileName, fileName);
        assertEquals(0, exitCode, "jar command failed");
        // verify the JAR file is created and contains the expected entry
        try (final JarFile jarFile = new JarFile(new File(targetJarFileName))) {
            final ZipEntry entry = jarFile.getEntry(fileName);
            assertNotNull(entry, "missing " + fileName + " entry in JAR file " + targetJarFileName);
        }
    }
}

