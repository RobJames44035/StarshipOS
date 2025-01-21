/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package pkg;

import pkg1.*;

/**
 * Another test class.
 */
public class AnotherClass {

    /**
     * A test field.
     */
    public RegClass field;

    /**
     * Constant field.
     */
    public static final String CONSTANT_FIELD_3 = "constant";

    /**
     * @deprecated don't use this field anymore.
     */
    public RegClass dep_field;

    /**
     * A sample enum.
     */
    public static enum ModalExclusionType {
        /**
         * Test comment.
         */
        NO_EXCLUDE,
        /**
         * Another comment.
         */
        APPLICATION_EXCLUDE
    };

    /**
     * A string constant.
     */
    public static final String CONSTANT1 = "C2";

    /**
     * A sample method.
     *
     * @param param some parameter.
     * @return a test object.
     */
    public Class method(pkg1.RegClass param) {
        return param;
    }
}
