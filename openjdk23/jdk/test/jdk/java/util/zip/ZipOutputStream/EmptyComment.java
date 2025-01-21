/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.io.ByteArrayOutputStream;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertThrows;

/**
 * @test
 * @bug 8277087
 * @summary Verifies various use cases when the zip comment should be empty
 * @run testng EmptyComment
 */
public final class EmptyComment {

    @DataProvider()
    Object[][] longLengths() {
        return new Object[][]{{0xFFFF + 1}, {0xFFFF + 2}, {0xFFFF * 2}};
    }

    /**
     * Overflow, the text is too long to be stored as a comment.
     */
    @Test(dataProvider = "longLengths")
    void testOverflow(int length) throws Exception {
        test(zos -> assertThrows(IllegalArgumentException.class, () -> {
            zos.setComment("X".repeat(length));
        }));
    }

    /**
     * Simple cases where the comment is set to the empty text.
     */
    @Test
    void testSimpleCases() throws Exception {
        test(zos -> {/* do nothing */});
        test(zos -> zos.setComment(null));
        test(zos -> zos.setComment(""));
        test(zos -> {
            zos.setComment("");
            zos.setComment(null);
        });
        test(zos -> {
            zos.setComment(null);
            zos.setComment("");
        });
        test(zos -> {
            zos.setComment("Comment");
            zos.setComment(null);
        });
        test(zos -> {
            zos.setComment("Comment");
            zos.setComment("");
        });
    }

    private static void test(Consumer<ZipOutputStream> test) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            test.accept(zos);

            zos.putNextEntry(new ZipEntry("x"));
            zos.finish();

            byte[] data = baos.toByteArray();

            if (data.length > 0xFFFF) { // just in case
                throw new RuntimeException("data is too big: " + data.length);
            }
            int pk = data.length - ZipFile.ENDHDR;
            if (data[pk] != 'P' || data[pk + 1] != 'K') {
                throw new RuntimeException("PK is not found");
            }
            // Since the comment is empty this will be two last bytes
            int pos = data.length - ZipFile.ENDHDR + ZipFile.ENDCOM;

            int len = (data[pos] & 0xFF) + ((data[pos + 1] & 0xFF) << 8);
            if (len != 0) {
                throw new RuntimeException("zip comment is not empty: " + len);
            }
        }
    }
}
