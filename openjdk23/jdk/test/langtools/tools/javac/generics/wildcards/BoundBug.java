/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4890134
 * @summary generics: used of ? get bound error
 * @author gafter
 *
 * @compile  BoundBug.java
 */

class BoundBug {
    class C {}

    class B<T extends C>
    {
        void foo() {
            B<? super T> con = null;   //ok
            B<? extends T> cov = null; //ok
            B<?>  biv = null;          //fails
            B<T>  inv = null;
        }
    }

    static
    {
        B<? super C> con = null;    //ok
        B<? extends C> cov = null;  //ok
        B<?>  biv = null;           //fails
        B<C>  inv = null;
    }
}
