/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

public class TestConstantValues {

    /**
     * Test 1 passes ({@value}).
     */
    public static final int TEST1PASSES = 500000;


    /**
     * Test 2 passes ({@value}).
     */
    public static final String TEST2PASSES = "My String";

    // all of if not most are in the JDK sources, it is
    // crucial we catch any discrepancies now.
    public static final byte BYTE_MAX_VALUE = 127;
    public static final byte BYTE_MIN_VALUE = -127;

    public static final char CHAR_MAX_VALUE = 65535;

    public static final double DOUBLE_MAX_VALUE = 1.7976931348623157E308;
    public static final double DOUBLE_MIN_VALUE = 4.9E-324;

    public static final float MAX_FLOAT_VALUE = 3.4028234663852886E38f;
    public static final float MIN_FLOAT_VALUE = 1.401298464324817E-45f;

    public static final boolean HELLO = true;
    public static final boolean GOODBYE = false;

    public static final int INT_MAX_VALUE = 2147483647;
    public static final int INT_MIN_VALUE = -2147483647;

    public static final long LONG_MAX_LONG_VALUE = 9223372036854775807L;
    public static final long LONG_MIN_LONG_VALUE = -9223372036854775808L;

    public static final short SHORT_MAX_VALUE = 32767;
    public static final short SHORT_MIN_VALUE = -32767;
}
