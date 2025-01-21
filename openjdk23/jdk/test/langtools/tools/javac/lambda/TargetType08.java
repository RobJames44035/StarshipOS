/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that explicit non-generic target type parses w/o problems
 * @author  Peter Levart
 * @author  Maurizio Cimadamore
 * @compile TargetType08.java
 */

class TargetType07 {

    public interface SAM1 { String m(); }
    public interface SAM2 { Comparable<?> m(); }

    public static void call(SAM1 s) { }
    public static void call(SAM2 s) { }

    public static void main(String[] args) {
        call((SAM1)()-> "Hello!" );
    }
}
