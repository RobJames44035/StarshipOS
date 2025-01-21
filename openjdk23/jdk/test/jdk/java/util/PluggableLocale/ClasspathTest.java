/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6388652 8062588 8210406 8174269
 * @summary Checks whether providers can be loaded from classpath.
 * @library providersrc/foobarutils
 *          providersrc/barprovider
 * @build com.foobar.Utils
 *        com.bar.*
 * @run main/othervm -Djava.locale.providers=CLDR,SPI ClasspathTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ClasspathTest {

    public static void main(String[] s) {
        new ClasspathTest();
    }

    ClasspathTest() {
        /*
         * Since providers can be loaded from the application's classpath,
         * this test will fail if they are NOT loaded from classpath.
         */
        Locale OSAKA = Locale.of("ja", "JP", "osaka");
        List<Locale> availableLocales = Arrays.asList(Locale.getAvailableLocales());
        if (!availableLocales.contains(OSAKA)) {
            throw new RuntimeException("LSS providers were NOT loaded from the class path.");
        }
    }
}
