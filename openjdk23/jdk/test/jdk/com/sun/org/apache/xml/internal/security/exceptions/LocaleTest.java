/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6454215
 * @summary Make sure there are no runtime errors when throwing Apache XML
 *      Security exceptions in a non-US default locale.
 * @modules java.xml.crypto/com.sun.org.apache.xml.internal.security.exceptions
 * @compile -XDignore.symbol.file LocaleTest.java
 * @run main LocaleTest
 */
import java.util.Locale;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;

public class LocaleTest {

    public static void main(String[] args) throws Exception {

        Locale reservedLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.ITALY);

            throw new XMLSecurityException("foo");
        } catch (XMLSecurityException xse) {
            System.out.println("Test PASSED");
        } catch (Throwable t) {
            System.out.println("Test FAILED");
            t.printStackTrace();
        } finally {
            // restore the reserved locale
            Locale.setDefault(reservedLocale);
        }
    }
}
