/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package pkg1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MethodOrder {
    /**
     * method test for ordering parameters
     * @return UsedClass something
     */
    public UsedClass m(){return null;}
    /**
     * method test for ordering parameters
     * @param i a param
     * @return UsedClass something
     */
    public UsedClass m(int i) {return null;}

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(int i1, int i2) {return null;}

    /**
     * method test for ordering parameters
     * @param array a param
     * @return something
     */
    public UsedClass m(byte[] array) {return null;}

    /**
     * method test for ordering parameters
     * @param in a param
     * @return something
     */
    public UsedClass m(Integer in) {return null;}

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(Integer i1, Integer i2) {return null;}

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(int i1, Integer i2) {return null;}

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(Integer i1, int i2) {return null;}

    /**
     * method test for ordering parameters
     * @param d a param
     * @return something
     */
    public UsedClass m(double d) {return null;}

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(double i1, double i2) {return null;}

    /**
     * method test for ordering parameters
     * @param in a param
     * @return something
     */
    public UsedClass m(Double in) {return null;}

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(Double i1, Double i2) {return null;}

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(double i1, Double i2) {return null;}

    /**
     * method test for ordering parameters
     * @param l1 param
     * @param xenon param
     * @return something
     */
    public UsedClass m(long l1, Long... xenon) {return null;}

    /**
     * method test for ordering parameters
     * @param l1 param
     * @return something
     */
    public UsedClass m(long l1) {return null;}

    /**
     *  method test for ordering parameters
     * @param l1 param
     * @param l2 param
     * @return something
     */
    public UsedClass m(long l1, Long l2) {return null;}

    /**
     *  method test for ordering parameters
     * @param l1 param
     * @param l2 param
     * @return something
     */
    public UsedClass m(long l1, long l2) {return null;}

    /**
     * method test for ordering parameters
     * @param array a param
     * @return something
     */
    public UsedClass m(Object[] array);

    /**
     * method test for ordering parameters
     * @param arrayarray two dimensional array
     * @return something
     */
    public UsedClass m(Object[][] arrayarray);

    /**
     * method test for ordering parameters
     * @param i1 a param
     * @param i2 a param
     * @return something
     */
    public UsedClass m(Double i1, double i2) {return null;}

    /**
     * method test for ordering parameters
     * @param collection a param
     * @return something
     */
    public UsedClass m(Collection collection) {return null;}

    /**
     * method test for ordering parameters
     * @param list a param
     * @return something
     */
    public UsedClass m(List list) {return null;}

    /**
     * method test for ordering parameters
     * @param collection a param
     * @return something
     */
    public UsedClass m(ArrayList<UsedClass> collection) {return null;}

    /**
     * method test for ordering parameters
     * @param u use a type param
     */
    public void tpm(UsedClass<?> u) {}

    /**
     * method test for ordering parameters
     * @param u1 use a type param
     * @param u2 use a type param
     */
    public void tpm(UsedClass<?> u1, UsedClass<?> u2) {}

    /**
     * method test for ordering parameters
     * @param u use a type param
     * @param array use a type param and an array
     */
    public void tpm(UsedClass<?> u, UsedClass<?>[] array) {}

    /**
     * method test for ordering parameters
     * @param u use type param with extends
     * @param a some string
     */
    public void tpm(UsedClass<? extends UsedClass> u, String a) {}
}
