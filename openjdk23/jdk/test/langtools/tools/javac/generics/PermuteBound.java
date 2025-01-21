/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4698375
 * @summary generics: subtyping problem when type parameters permuted
 * @author gafter
 *
 * @compile  PermuteBound.java
 */

package PermuteBound;

class C<X, Y> {}

class D<X, Y> extends C<X, Y>
{
    void f(C<X, Y> c)
    {
        D<X, Y> d = (D<X, Y>) c;// OK because D extends C
    }

    void g(C<Y, X> c)// parameters X and Y are now permuted
    {
        D<Y, X> d = (D<Y, X>) c;// should also be OK but is not !?
        //                    ^
        // inconvertible types
        // found :    C<Y,X>
        // required : D<Y,X>
    }
    /*
    void gWorkAround(C<Y, X> c)// but generates unchecked warning
    {
        D<Y, X> d = (D) ((C) c);
    }
    */
}
