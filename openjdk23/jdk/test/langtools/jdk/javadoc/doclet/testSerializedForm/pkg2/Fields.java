/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package pkg2;

public class Fields<E> implements java.io.Serializable {
    /** Some doc. */
    public Class<E> someClass;

    /** a primitive array */
    private int[] a1;

    /** a two dimensional primitive array */
    private int[][] a2;

    /** a single object array */
    private Fields[] singleArray;

    /** a double object array */
    private Fields[][] doubleArray;

}
