/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package pkg3;

public @interface RT {

    /** The CONSTANT */
    static String CONSTANT = "constant";
    /**
     *  The value is {@value CONSTANT}.
     *  @return a value
     */
     int value();

    /**
     *  The value1 is {@value #CONSTANT}.
     *  @return a value
     */
    int value1();

    /**
     *  The value2 is {@value pkg3.RT#CONSTANT}.
     *  @return a value
     */
     int value2();

    /**
     *  The value3 is {@value pkg2.Class3#TEST_12_PASSES}.
     *  @return a value
     */
    int value3();

}
