/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that explicit generic target type parses w/o problems
 * @author  Peter Levart
 * @author  Maurizio Cimadamore
 * @compile TargetType07.java
 */

class TargetType07 {

    public interface SAM1<X> { X m(); }
    public interface SAM2<X> { X m(); }

    public static <X> void call(SAM1<X> s) { }
    public static <X> void call(SAM2<X> s) { }

    public static void main(String[] args) {
        call((SAM1<Integer>)()-> 1 );
    }
}
