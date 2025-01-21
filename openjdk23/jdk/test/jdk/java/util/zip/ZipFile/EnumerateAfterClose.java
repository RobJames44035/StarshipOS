/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
   @bug 4290060
   @summary Check if the zip file is closed before access any
            elements in the Enumeration.
   @run junit EnumerateAfterClose
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnumerateAfterClose {

    // ZIP file used in this test
    private Path zip = Path.of("enum-after-close.zip");

    /**
     * Create a sample ZIP file for use by this test
     * @throws IOException if an unexpected IOException occurs
     */
    @BeforeEach
    public void setUp() throws IOException {
        try (OutputStream out = Files.newOutputStream(zip);
             ZipOutputStream zo = new ZipOutputStream(out)) {
            zo.putNextEntry(new ZipEntry("file.txt"));
            zo.write("hello".getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Delete the ZIP file produced by this test
     * @throws IOException if an unexpected IOException occurs
     */
    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(zip);
    }

    /**
     * Attempting to using a ZipEntry Enumeration after its backing
     * ZipFile is closed should throw IllegalStateException.
     *
     * @throws IOException if an unexpected IOException occurs
     */
    @Test
    public void enumeratingAfterCloseShouldThrowISE() throws IOException {
        // Retain a reference to an enumeration backed by a closed ZipFile
        Enumeration e;
        try (ZipFile zf = new ZipFile(zip.toFile())) {
            e = zf.entries();
        }
        // Using the enumeration after the ZipFile is closed should throw ISE
        assertThrows(IllegalStateException.class, () -> {
            if (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry)e.nextElement();
            }
        });
    }
}
