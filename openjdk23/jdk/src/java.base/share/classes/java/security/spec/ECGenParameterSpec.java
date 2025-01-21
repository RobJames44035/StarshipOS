/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package java.security.spec;

/**
 * This immutable class specifies the set of parameters used for
 * generating elliptic curve (EC) domain parameters.
 *
 * @see AlgorithmParameterSpec
 *
 * @author Valerie Peng
 *
 * @since 1.5
 */
public class ECGenParameterSpec extends NamedParameterSpec {

    /**
     * Creates a parameter specification for EC parameter
     * generation using a standard (or predefined) name
     * {@code stdName} in order to generate the corresponding
     * (precomputed) elliptic curve domain parameters. For the
     * list of supported names, please consult the documentation
     * of the provider whose implementation will be used.
     *
     * @param stdName the standard name of the to-be-generated EC
     *        domain parameters. See the ECGenParameterSpec section in the
     *        <a href=
     *        "{@docRoot}/../specs/security/standard-names.html#ecgenparameterspec">
     *        Java Security Standard Algorithm Names Specification</a> for
     *        information about standard names.
     * @throws NullPointerException if {@code stdName} is null.
     */
    public ECGenParameterSpec(String stdName) {
        super(stdName);
    }
}

