/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/*
 * @test
 * @bug 4358774
 * @run testng NullOutputStream
 * @summary Check for expected behavior of OutputStream.nullOutputStream().
 */
public class NullOutputStream {
    private static OutputStream openStream;
    private static OutputStream closedStream;

    @BeforeClass
    public static void setup() {
        openStream = OutputStream.nullOutputStream();
        closedStream = OutputStream.nullOutputStream();
        try {
           closedStream.close();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @AfterClass
    public static void closeStream() {
        try {
            openStream.close();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public static void testOpen() {
        assertNotNull(openStream,
            "OutputStream.nullOutputStream() returned null");
    }

    @Test
    public static void testWrite() {
        try {
            openStream.write(62832);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public static void testWriteBII() {
        try {
            openStream.write(new byte[] {(byte)6}, 0, 1);
        } catch (Exception e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public static void testWriteClosed() {
        try {
            closedStream.write(62832);
            fail("Expected IOException not thrown");
        } catch (IOException e) {
        }
    }

    @Test
    public static void testWriteBIIClosed() {
        try {
            closedStream.write(new byte[] {(byte)6}, 0, 1);
            fail("Expected IOException not thrown");
        } catch (IOException e) {
        }
    }
}
