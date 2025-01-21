/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 4884238 8310047
 * @summary Test standard charset name constants and class qualities.
 * @author Mike Duigou
 * @run junit Standard
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Standard {

    // These are the charsets StandardCharsets.java is expected to contain.
    private static final String[] expectedCharsets = {
            "US-ASCII", "ISO-8859-1", "UTF-8",
            "UTF-16BE", "UTF-16LE", "UTF-16",
            "UTF-32BE", "UTF-32LE", "UTF-32"
    };

    private static final Field[] standardCharsetFields =
            StandardCharsets.class.getFields();

    /**
     * Validates that the Charset constants from the data provider
     * are of type Charset.
     */
    @ParameterizedTest
    @MethodSource("charsetProvider")
    public void typeTest(Charset charset) {
        // Doubly checked, as it is validated when passed as a param
        assertTrue(charset instanceof Charset);
    }

    /**
     * Validates that calling .name() on a Charset constant is equal
     * to the matching String value from the data provider.
     */
    @ParameterizedTest
    @MethodSource("charsetProvider")
    public void nameMethodTest(Charset charset, String charString) {
        assertEquals(charset.name(), charString);
    }

    /**
     * Validates that calling Charset.forName() on a String is equal
     * to the matching Charset constant from the data provider.
     */
    @ParameterizedTest
    @MethodSource("charsetProvider")
    public void forNameMethodTest(Charset charset, String charString) {
        assertEquals(Charset.forName(charString), charset);
    }

    /**
     * Validates the qualities of a StandardCharsets field are as expected:
     * The field is final, static, public, and one can access
     * the underlying value of the field.
     */
    @ParameterizedTest
    @MethodSource("charsetFields")
    public void charsetModifiersTest(Field charsetField) throws IllegalAccessException {
        // Check modifiers
        assertEquals(StandardCharsets.class, charsetField.getDeclaringClass());
        assertTrue(Modifier.isFinal(charsetField.getModifiers()));
        assertTrue(Modifier.isStatic(charsetField.getModifiers()));
        assertTrue(Modifier.isPublic(charsetField.getModifiers()));
        // Check that the value can be accessed, and it is a Charset
        Object valueOfField = charsetField.get(null);
        assertTrue(valueOfField instanceof Charset);
    }

    /**
     * Validates that the Charsets contained in StandardCharsets are equal
     * to the expected Charsets list defined in the test. This test should fail if
     * either the actual or expected (standard) Charsets are modified, and
     * the others are not.
     */
    @Test
    public void correctCharsetsTest() {
        // Grab the value from each Standard Charset field
        List<String> actualCharsets = charsetFields().map(field -> {
            try {
                return ((Charset) field.get(null)).name();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Can not test correctCharsetsTest() due to %s", e);
            }
        }).toList();
        assertEquals(actualCharsets, Arrays.asList(expectedCharsets));
    }

    /**
     * Provides the constant Charset and associated String value of
     * the standard charsets.
     */
    private static Stream<Arguments> charsetProvider() {
        return Stream.of(
                Arguments.of(StandardCharsets.US_ASCII, "US-ASCII"),
                Arguments.of(StandardCharsets.ISO_8859_1, "ISO-8859-1"),
                Arguments.of(StandardCharsets.UTF_8, "UTF-8"),
                Arguments.of(StandardCharsets.UTF_16BE, "UTF-16BE"),
                Arguments.of(StandardCharsets.UTF_16LE, "UTF-16LE"),
                Arguments.of(StandardCharsets.UTF_16, "UTF-16"),
                Arguments.of(StandardCharsets.UTF_32BE, "UTF-32BE"),
                Arguments.of(StandardCharsets.UTF_32LE, "UTF-32LE"),
                Arguments.of(StandardCharsets.UTF_32, "UTF-32")
        );
    }

    private static Stream<Field> charsetFields() {
        return Arrays.stream(standardCharsetFields);
    }
}
