/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

package javax.tools;

/**
 * Interface for recognizing options.
 *
 * @since 1.6
 */
public interface OptionChecker {

    /**
     * Determines if the given option is supported and if so, the
     * number of arguments the option takes.
     *
     * @param option an option
     * @return the number of arguments the given option takes or -1 if
     * the option is not supported
     */
    int isSupportedOption(String option);

}
