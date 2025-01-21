/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

// dummy package
package nsk.jdi.ReferenceType.dummyPackage;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is an auxiliary class outside a main debuggee package. It's
 * used to provoke IllegalArgumentException in the debugger:
 * non-public fields cannot be accessed outside the package.
 */
public class getvalues003a {
// dummy private fields
    private static boolean boolPrFld = false;
    private static byte bytePrFld = 127;
    private static char charPrFld = 'b';
    private static double doublePrFld = 6.2D;
    private static float floatPrFld = 5.1F;
    private static int intPrFld = 2147483647;
    private static long longPrFld = 9223372036854775807L;
    private static short shortPrFld = -32768;

// dummy fields with default access
    static boolean boolFld = false;
    static byte byteFld = 127;
    static char charFld = 'a';
    static double doubleFld = 6.2D;
    static float floatFld = 5.1F;
    static int intFld = 2147483647;
    static long longFld = 9223372036854775807L;
    static short shortFld = -32768;

// dummy protected fields
    protected static boolean boolProtFld = true;
    protected static byte byteProtFld = Byte.MIN_VALUE;
    protected static char charProtFld = 'c';
    protected static double doubleProtFld = Double.MAX_VALUE;
    protected static float floatProtFld = Float.MAX_VALUE;
    protected static int intProtFld = Integer.MIN_VALUE;
    protected static long longProtFld = Long.MIN_VALUE;
    protected static short shortProtFld = Short.MAX_VALUE;
}
