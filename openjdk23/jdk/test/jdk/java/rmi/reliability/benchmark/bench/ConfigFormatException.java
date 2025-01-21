/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 *
 */

package bench;

/**
 * Exception that is thrown if harness config file doesn't obey proper syntax.
 */
public class ConfigFormatException extends Exception {
    /**
     * Construct blank ConfigFormatException.
     */
    public ConfigFormatException() {
    }

    /**
     * Construct new ConfigFormatException with the given error string.
     */
    public ConfigFormatException(String s) {
        super(s);
    }
}
