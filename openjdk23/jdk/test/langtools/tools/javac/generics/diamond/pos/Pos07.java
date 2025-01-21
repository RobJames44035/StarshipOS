/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6939620 6894753 7020044
 *
 * @summary  Diamond and intersection types
 * @author mcimadamore
 * @compile Pos07.java
 *
 */

class Pos07 {
    static class Foo<X extends Number & Comparable<Number>> {}
    static class DoubleFoo<X extends Number & Comparable<Number>,
                           Y extends Number & Comparable<Number>> {}
    static class TripleFoo<X extends Number & Comparable<Number>,
                           Y extends Number & Comparable<Number>,
                           Z> {}

    Foo<?> fw = new Foo<>();
    DoubleFoo<?,?> dw = new DoubleFoo<>();
    TripleFoo<?,?,?> tw = new TripleFoo<>();
}
