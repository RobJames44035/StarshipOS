/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

public class C extends Parent {

    /**
     * @param param1 testing 1 2 3.
     * @param param2 testing 1 2 3.
     */
    public void regularParams(int param1, int param2) {}

    /**
     * @param p1 testing 1 2 3.
     * @param p2 testing 1 2 3.
     * @param inheritBug {@inheritDoc}
     */
    @Override
    public void nonMatchingParams(int param1, int param2) {}

    /**
     * Overriding method with missing/additional/duplicate param tags in non-declaration order.
     *
     * @param x does not exist
     * @param b a boolean
     * @param i an int
     * @param x duplicate
     * @param b another duplicate
     */
    @Override
    public void unorderedParams(int i, double d, boolean b) {}

    /**
     * Generic method with mixed/missing param tags.
     *
     * @param t1 param 1
     * @param <T2> type 2
     * @param t3 param 3
     */
    public static <T1, T2, T3> void genericMethod(T1 t1, T2 t2, T3 t3) {}

    /**
     * A partially documented point.
     *
     * @param y the y coordinate
     */
    public static record Point(int x, int y) {}

    /**
     * Generic class with missing param tags.
     *
     * @param <T1> type 1
     */
    public static class Nested<T1, T2> {}

}
