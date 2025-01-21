/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  issues with lambda conversion involving generic class hierarchies
 * @author  Maurizio Cimadamore
 * @compile LambdaConv11.java
 */

import java.util.Comparator;

class LambdaConv11<T> {

    interface SAM<X> extends Comparator<X> {
        public int compare(X left, X right);
    }

    SAM<T> y = (l, r) -> 0;
}
