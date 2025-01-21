/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package gc.testlibrary;

public class Allocation {
    public static volatile Object obj;

    /**
     * This code assigns the object to a "public static volatile" variable. The
     * compiler does not seem capable of optimizing through this (yet). Any object
     * allocated and sent to this method ought not to be optimized away.
     *
     * @param obj The allocation of this object will not be optimized away.
     */

    public static void blackHole(Object obj) {
        Allocation.obj = obj;
        Allocation.obj = null;
    }
}
