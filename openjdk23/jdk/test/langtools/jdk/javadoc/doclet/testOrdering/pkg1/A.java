/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package pkg1;
import java.util.Collection;
import java.util.List;
public class A {

    /**
     * Constructor
     * @param a class
     */
    public A(UsedClass a) {}

    /**
     * test inner classes
     */
    public static class C {

        /**
         * inner classes constructor
         * @param u a param
         * @param array a param
         */
        public C(UsedClass u, Object[] array){}

        /**
         * inner classes constructor
         * @param u a param
         * @param collection a param
         */
        public C(UsedClass u, Collection collection){}

        /**
         * inner classes constructor
         * @param u a param
         * @param list a param
         */
        public C(UsedClass u, List list){}
    }
}
