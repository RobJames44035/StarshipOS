/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 *
 * @test
 * @bug 8176841
 * @summary Tests LocaleNameProvider SPIs
 * @library provider
 * @build provider/module-info provider/foo.LocaleNameProviderImpl
 * @run main/othervm -Djava.locale.providers=SPI LocaleNameProviderTests
 */

import java.util.Locale;

/**
 * Test LocaleNameProvider SPI with BCP47 U extensions
 *
 * Verifies getUnicodeExtensionKey() and getUnicodeExtensionType() methods in
 * LocaleNameProvider works.
 */
public class LocaleNameProviderTests {
    private static final String expected = "foo (foo_ca:foo_japanese)";

    public static void main(String... args) {
        String name = Locale.forLanguageTag("foo-u-ca-japanese").getDisplayName(Locale.of("foo"));
        if (!name.equals(expected)) {
            throw new RuntimeException("Unicode extension key and/or type name(s) is incorrect. " +
                "Expected: \"" + expected + "\", got: \"" + name + "\"");
        }
    }
}
