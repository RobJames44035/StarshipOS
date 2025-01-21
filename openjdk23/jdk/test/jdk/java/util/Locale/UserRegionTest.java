/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8342582
 * @summary Test if "user.region" system property successfully overrides
 *          other locale related system properties at startup
 * @modules jdk.localedata
 * @run junit/othervm
 *      -Duser.region=DE
 *      -Duser.language=en
 *      -Duser.script=Latn
 *      -Duser.country=US
 *      -Duser.variant=FOO UserRegionTest
 * @run junit/othervm
 *      -Duser.region=DE_POSIX
 *      -Duser.language=en
 *      -Duser.script=Latn
 *      -Duser.country=US
 *      -Duser.variant=FOO UserRegionTest
 * @run junit/othervm
 *      -Duser.region=_POSIX
 *      -Duser.language=en
 *      -Duser.script=Latn
 *      -Duser.country=US
 *      -Duser.variant=FOO UserRegionTest
 */

import java.util.Locale;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRegionTest {
    @Test
    public void testDefaultLocale() {
        var region = System.getProperty("user.region").split("_");
        var expected = Locale.of(System.getProperty("user.language"),
                region[0], region.length > 1 ? region[1] : "");
        assertEquals(expected, Locale.getDefault());
        assertEquals(expected, Locale.getDefault(Locale.Category.FORMAT));
        assertEquals(expected, Locale.getDefault(Locale.Category.DISPLAY));
    }

    @Test
    public void testNumberFormat() {
        if (System.getProperty("user.region").startsWith("DE")) {
            assertEquals("0,50000", String.format("%.5f", 0.5f));
        } else {
            assertEquals("0.50000", String.format("%.5f", 0.5f));
        }
    }
}
