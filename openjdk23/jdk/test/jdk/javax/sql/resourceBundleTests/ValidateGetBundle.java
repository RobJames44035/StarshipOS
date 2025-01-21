/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import com.sun.rowset.JdbcRowSetResourceBundle;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

/**
 * @test
 * @bug 8294989
 * @summary Check JDBC RowSet resource bundle access
 * @modules java.sql.rowset/com.sun.rowset:+open
 * @run junit/othervm ValidateGetBundle
 */
public class ValidateGetBundle{

    // Data provider for testResourceBundleAccess
    private static Stream<Arguments> bundleProvider() {
        return Stream.of(
                // The resource bundle should be found with the fully qualified class name
                Arguments.of("com.sun.rowset.RowSetResourceBundle", true),
                // The resource bundle will not be found when the path is specified
                Arguments.of("com/sun/rowset/RowSetResourceBundle", false)
        );
    }

    /**
     * Test to validate whether the JDBC RowSet Resource bundle can be found
     * @param bundleName the base name of the resource bundle
     * @param expectBundle indicates whether the resource bundle should be found
     */
    @ParameterizedTest
    @MethodSource("bundleProvider")
    void testResourceBundleAccess(String bundleName, boolean expectBundle) {
        try {
            var bundle = ResourceBundle.getBundle(bundleName,
                    Locale.US, JdbcRowSetResourceBundle.class.getModule());
            if (!expectBundle) {
                throw new RuntimeException(
                        String.format("$$$ Error: '%s' shouldn't have loaded!%n", bundleName));
            }
            System.out.printf("$$$ %s was found as expected!%n", bundleName);
        } catch (MissingResourceException mr) {
            if (expectBundle) {
                throw new RuntimeException(
                        String.format("$$$ Error: '%s' should have loaded!", bundleName), mr);
            }
            System.out.printf("$$$ %s was not found as expected!", bundleName);
        }
    }
}
