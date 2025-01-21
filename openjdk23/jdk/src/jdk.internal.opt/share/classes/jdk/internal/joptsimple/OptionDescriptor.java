/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import java.util.List;

/**
 * Describes options that an option parser recognizes, in ways that might be useful to {@linkplain HelpFormatter
 * help screens}.
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public interface OptionDescriptor {
    /**
     * A set of options that are mutually synonymous.
     *
     * @return synonymous options
     */
    List<String> options();

    /**
     * Description of this option's purpose.
     *
     * @return a description for the option
     */
    String description();

    /**
     * What values will the option take if none are specified on the command line?
     *
     * @return any default values for the option
     */
    List<?> defaultValues();

    /**
     * Is this option {@linkplain ArgumentAcceptingOptionSpec#required() required} on a command line?
     *
     * @return whether the option is required
     */
    boolean isRequired();

    /**
     * Does this option {@linkplain ArgumentAcceptingOptionSpec accept arguments}?
     *
     * @return whether the option accepts arguments
     */
    boolean acceptsArguments();

    /**
     * Does this option {@linkplain OptionSpecBuilder#withRequiredArg() require an argument}?
     *
     * @return whether the option requires an argument
     */
    boolean requiresArgument();

    /**
     * Gives a short {@linkplain ArgumentAcceptingOptionSpec#describedAs(String) description} of the option's argument.
     *
     * @return a description for the option's argument
     */
    String argumentDescription();

    /**
     * Gives an indication of the {@linkplain ArgumentAcceptingOptionSpec#ofType(Class) expected type} of the option's
     * argument.
     *
     * @return a description for the option's argument type
     */
    String argumentTypeIndicator();

    /**
     * Tells whether this object represents the non-option arguments of a command line.
     *
     * @return {@code true} if this represents non-option arguments
     */
    boolean representsNonOptions();
}
