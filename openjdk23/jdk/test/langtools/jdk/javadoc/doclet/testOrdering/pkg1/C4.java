/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package pkg1;

public class C4 {
    /**
     * Field in C4.
     */
    public UsedClass fieldInC4;

    /**
     * A duplicated field
     */
    public UsedClass zfield;

    /**
     * Method in C4.
     * @param p a param
     * @return UsedClass
     */
    public UsedClass methodInC4(UsedClass p) {return p;}

    /**
     * A duplicated method to test ordering
     * @param p a param
     * @return UsedClass
     */
    public UsedClass zmethod(UsedClass p) {return p;}
}
