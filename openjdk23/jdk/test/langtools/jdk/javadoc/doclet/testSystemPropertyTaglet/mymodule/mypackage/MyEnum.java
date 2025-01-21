/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package mypackage;

/**
 * (enum) the {@systemProperty test.property} system property.
 */
public enum MyEnum {

    /**
     * (enum/constant) the {@systemProperty test.property} system property.
     */
    X {
        // The mention below will not appear in the javadoc, see JDK-8144631

        /**
         * (enum/constant-specific-method) the {@systemProperty test.property} system property.
         */
        public void m() { }
    };

    /**
     * (enum/method) the {@systemProperty test.property} system property.
     */
    public void m() { }
}