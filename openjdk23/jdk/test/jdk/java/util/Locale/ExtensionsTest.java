/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7168528
 * @summary Test Locale.hasExtensions() and Locale.stripExtensions().
 */

import java.util.*;

public class ExtensionsTest {
    public static void main(String[] args) {
        Locale jaJPJP = Locale.of("ja", "JP", "JP");
        if (!jaJPJP.hasExtensions()) {
            error(jaJPJP + " should have an extension.");
        }
        Locale stripped = jaJPJP.stripExtensions();
        if (stripped.hasExtensions()) {
            error(stripped + " should NOT have an extension.");
        }
        if (jaJPJP.equals(stripped)) {
            throw new RuntimeException("jaJPJP equals stripped");
        }
        if (!"ja-JP-x-lvariant-JP".equals(stripped.toLanguageTag())) {
            error("stripped.toLanguageTag() isn't ja-JP-x-lvariant-JP");
        }

        Locale enUSja = Locale.forLanguageTag("en-US-u-ca-japanese");
        if (!enUSja.stripExtensions().equals(Locale.US)) {
            error("stripped enUSja not equals Locale.US");
        }

        // If a Locale has no extensions, stripExtensions() returns self.
        Locale enUS = Locale.US.stripExtensions();
        if (enUS != Locale.US) {
            error("stripped Locale.US != Locale.US");
        }
    }

    private static void error(String msg) {
        throw new RuntimeException(msg);
    }
}
