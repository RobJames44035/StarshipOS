/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6312358
 * @summary Verify that an NPE is thrown by invoking Locale.getInstance() with
 * any argument being null.
 * @modules java.base/java.util:open
 * @run junit GetInstanceCheck
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.fail;

public class GetInstanceCheck {

    static Method getInstanceMethod;
    static final String NAME = "getInstance";

    /**
     * Initialize the non-public Locale.getInstance() method.
     */
    @BeforeAll
    static void initializeMethod() {
        try {
            // Locale.getInstance is not directly accessible.
            getInstanceMethod = Locale.class.getDeclaredMethod(
                    NAME, String.class, String.class, String.class
            );
            getInstanceMethod.setAccessible(true);
        } catch (java.lang.NoSuchMethodException exc) {
            // The test should fail if we can not test the desired method
            fail(String.format("Tried to get the method '%s' which was not found," +
                    " further testing is not possible, failing test", NAME));
        }
    }

    /**
     * Exists as sanity check that Locale.getInstance() will not throw
     * an NPE if no arguments are null.
     */
    @ParameterizedTest
    @MethodSource("passingArguments")
    public void noNPETest(String language, String country, String variant)
            throws IllegalAccessException {
        try {
            getInstanceMethod.invoke(null, language, country, variant);
        } catch (InvocationTargetException exc) {
            // Determine underlying exception
            Throwable cause = exc.getCause();
            if (exc.getCause() instanceof NullPointerException) {
                fail(String.format("%s should not be thrown when no args are null", cause));
            } else {
                fail(String.format("%s unexpectedly thrown, when no exception should be thrown", cause));
            }
        }
    }

    /**
     * Make sure the Locale.getInstance() method throws an NPE
     * if any given argument is null.
     */
    @ParameterizedTest
    @MethodSource("failingArguments")
    public void throwNPETest(String language, String country, String variant)
            throws IllegalAccessException {
        try {
            getInstanceMethod.invoke(null, language, country, variant);
            fail("Should NPE with any argument set to null");
        } catch (InvocationTargetException exc) {
            // Determine underlying exception
            Throwable cause = exc.getCause();
            if (cause instanceof NullPointerException) {
                System.out.println("NPE successfully thrown");
            } else {
                fail(cause + " is thrown, when NPE should have been thrown");
            }
        }
    }

    private static Stream<Arguments> passingArguments() {
        return Stream.of(
                Arguments.of("null", "GB", ""),
                Arguments.of("en", "null", ""),
                Arguments.of("en", "GB", "null")
        );
    }

    private static Stream<Arguments> failingArguments() {
        return Stream.of(
                Arguments.of(null, "GB", ""),
                Arguments.of("en", null, ""),
                Arguments.of("en", "GB", null)
        );
    }
}
