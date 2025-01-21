/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @test
 * @bug 8276970
 * @summary Test to verify the charset in PrintStream is inherited
 *      in the OutputStreamWriter/PrintWriter
 * @run testng InheritEncodingTest
 */
@Test
public class InheritEncodingTest {

    private static final String testString = "\u00e9\u3042"; // "éあ"

    @DataProvider
    public Object[][] encodings() {
        return new Object[][]{
                {StandardCharsets.ISO_8859_1},
                {StandardCharsets.US_ASCII},
                {StandardCharsets.UTF_8},
                {StandardCharsets.UTF_16},
                {StandardCharsets.UTF_16BE},
                {StandardCharsets.UTF_16LE},
        };
    }

    @Test (dataProvider = "encodings")
    public void testOutputStreamWriter(Charset stdCharset) throws IOException {
        var ba = new ByteArrayOutputStream();
        var ps = new PrintStream(ba, true, stdCharset);
        var expected = new String(testString.getBytes(stdCharset), stdCharset);

        // tests OutputStreamWriter's encoding explicitly
        var osw = new OutputStreamWriter(ps);
        assertEquals(Charset.forName(osw.getEncoding()), stdCharset);

        // tests roundtrip result
        osw.write(testString);
        osw.flush();
        var result = ba.toString(stdCharset);
        assertEquals(result, expected);
    }

    @Test (dataProvider = "encodings")
    public void testPrintWriter(Charset stdCharset) throws IOException {
        var ba = new ByteArrayOutputStream();
        var ps = new PrintStream(ba, true, stdCharset);
        var expected = new String(testString.getBytes(stdCharset), stdCharset);

        // tests roundtrip result
        var pw = new PrintWriter(ps);
        pw.write(testString);
        pw.flush();
        var result = ba.toString(stdCharset);
        assertEquals(result, expected);
    }

    @Test (dataProvider = "encodings")
    public void testPrintStream(Charset stdCharset) throws IOException {
        var ba = new ByteArrayOutputStream();
        var ps = new PrintStream(ba, true, stdCharset);
        var expected = new String(testString.getBytes(stdCharset), stdCharset);

        // tests PrintStream's charset explicitly
        var psWrapper = new PrintStream(ps);
        assertEquals(psWrapper.charset(), stdCharset);

        // tests roundtrip result
        psWrapper.print(testString);
        psWrapper.flush();
        var result = ba.toString(stdCharset);
        assertEquals(result, expected);
    }
}
