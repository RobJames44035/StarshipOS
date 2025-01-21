/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 4184873
 * @summary test that locale invariants are preserved across serialization.
 * @run junit LegacyCodesClassInvariant
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *  A Locale can never contain the following language codes: he, yi or id.
 */
public class LegacyCodesClassInvariant {
    @Test
    public void testIt() throws Exception {
        verify("he");
        verify("yi");
        verify("id");
    }

    private void verify(String lang) {
        try {
            ObjectInputStream in = getStream(lang);
            if (in != null) {
                final Locale loc = (Locale)in.readObject();
                final Locale expected = Locale.of(lang, "XX");
                assertEquals(expected, loc,
                        "Locale didn't maintain invariants for: "+lang);
                in.close();
            }
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    private ObjectInputStream getStream(String lang) {
        try {
            final File f = new File(System.getProperty("test.src", "."), "LegacyCodesClassInvariant_"+lang);
            return new ObjectInputStream(new FileInputStream(f));
        } catch (Exception e) {
            fail(e.toString());
            return null;
        }
    }
}
