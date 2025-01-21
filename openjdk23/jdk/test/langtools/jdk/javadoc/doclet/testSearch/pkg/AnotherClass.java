/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package pkg;

import pkg1.*;

/**
 * Another test class. Testing empty {@index }.
 */
public class AnotherClass {

    /**
     * A test field. Testing only white-spaces in index tag text {@index       }.
     */
    public RegClass field;

    /**
     * Constant field. Testing no text in index tag {@index}.
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
         * Test comment. Testing inline tag inside index tag {@index "nested {@index nested_tag_test}"}
         */
        NO_EXCLUDE,
        /**
         * Another comment. Testing HTML inside index tag {@index "html <span> see </span>"}
         */
        APPLICATION_EXCLUDE
    };

    /**
     * A string constant. Testing {@index "quoted"no-space}.
     */
    public static final String CONSTANT1 = "C2";

    /**
     * A sample method. Testing search tag for {@index "unclosed quote}.
     *
     * @param param some parameter.
     * @return a test object.
     */
    public pkg1.RegClass method(pkg1.RegClass param) {
        return param;
    }

    /**
     * Method to test member search index URL. Testing search tag for {@index trailing backslash\}
     *
     * @param testArray some test array.
     * @param testInt some test int.
     * @param testString some test string.
     */
    public void method(byte[] testArray, int testInt, String testString) {
    }
}
