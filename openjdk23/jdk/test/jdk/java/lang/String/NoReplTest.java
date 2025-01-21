/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8286287 8288589
 * @summary Tests for *NoRepl() shared secret methods.
 * @run testng NoReplTest
 * @modules jdk.charsets
 */

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HexFormat;
import static java.nio.charset.StandardCharsets.UTF_16;

import org.testng.annotations.Test;

@Test
public class NoReplTest {
    private final static byte[] MALFORMED_UTF16 = {(byte)0x00, (byte)0x20, (byte)0x00};
    private final static String MALFORMED_WINDOWS_1252 = "\u0080\u041e";
    private final static Charset WINDOWS_1252 = Charset.forName("windows-1252");

    /**
     * Verifies newStringNoRepl() throws a CharacterCodingException.
     * The method is invoked by `Files.readString()` method.
     */
    @Test
    public void newStringNoReplTest() throws IOException {
        var f = Files.createTempFile(null, null);
        try (var fos = Files.newOutputStream(f)) {
            fos.write(MALFORMED_UTF16);
            var read = Files.readString(f, UTF_16);
            throw new RuntimeException("Exception should be thrown for a malformed input. Bytes read: " +
                    HexFormat.of()
                            .withPrefix("x")
                            .withUpperCase()
                            .formatHex(read.getBytes(UTF_16)));
        } catch (CharacterCodingException cce) {
            // success
        } finally {
            Files.delete(f);
        }
    }

    /**
     * Verifies getBytesNoRepl() throws a CharacterCodingException.
     * The method is invoked by `Files.writeString()` method.
     */
    @Test
    public void getBytesNoReplTest() throws IOException {
        var f = Files.createTempFile(null, null);
        try {
            Files.writeString(f, MALFORMED_WINDOWS_1252, WINDOWS_1252);
            throw new RuntimeException("Exception should be thrown");
        } catch (CharacterCodingException cce) {
            // success
        } finally {
            Files.delete(f);
        }
    }
}
