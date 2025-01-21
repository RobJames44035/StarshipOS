/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package pkg2;

import pkg1.*;

/**
 * Another test class.
 */
public class C2 {

    /**
     * A test field.
     */
    public C1 field;

    /**
     * @deprecated don't use this field anymore.
     */
    public C1 dep_field;

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
    public C1 method(C1 param) {
        return param;
    }
}
