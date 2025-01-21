/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 4185816
 * @library /java/text/testlib
 * @build Bug4185816Test HexDumpReader
 * @run junit Bug4185816Test
 * @summary test that MessageFormat invariants are preserved across serialization.
 */

import java.util.*;
import java.io.*;
import java.text.ChoiceFormat;
import java.text.MessageFormat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 *  A Locale can never contain language codes of he, yi or id.
 */
public class Bug4185816Test {
    private static final String FILE_NAME = "Bug4185816.ser";
    private static final String CORRUPT_FILE_NAME = "Bug4185816Corrupt.ser";

    @Test
    public void testIt() throws Exception {
        Exception e = checkStreaming(FILE_NAME);
        if (e != null) {
            fail("MessageFormat did not stream in valid stream: "+e);
            e.printStackTrace();
        }
        e = checkStreaming(CORRUPT_FILE_NAME);
        if (!(e instanceof InvalidObjectException)) {
            fail("MessageFormat did NOT detect corrupt stream: "+e);
            e.printStackTrace();
        }
    }

    public Exception checkStreaming(final String fileName) {
        try {
            final InputStream is = HexDumpReader.getStreamFromHexDump(fileName + ".txt");
            final ObjectInputStream in = new ObjectInputStream(is);
            final MessageFormat form = (MessageFormat)in.readObject();
            final Object[] testArgs = {12373L, "MyDisk"};
            final String result = form.format(testArgs);
            in.close();
        } catch (Exception e) {
            return e;
        }
        return null;
    }
}
