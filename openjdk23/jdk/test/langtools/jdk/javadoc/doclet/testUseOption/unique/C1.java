/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package unique;

/**
 * A class to ensure we get unique entries in class-use for each of these,
 * so make sure each method is uniquely named to avoid false positives
 */

public class C1 {

    /**
     * Ctor C1 to test uniqueness
     * @param a param
     * @param b param
     */
    public C1(UseMe a, UseMe b){}

    /**
     * umethod1 to test uniqueness
     * @param one param
     * @param uarray param
     */
    public void umethod1(UseMe<?> one, UseMe<?>[] uarray){}

    /**
     * umethod1 to test uniqueness
     * @param one param
     * @param two param
     */
    public void umethod2(UseMe<?> one, UseMe<?> two){}

    /**
     * umethod1 to test uniqueness
     * @param a param
     * @param b param
     */
    public void umethod3(UseMe a, UseMe b){}
}
