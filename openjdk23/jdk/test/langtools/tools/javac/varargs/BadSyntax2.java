/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4901611 5009693 4920438
 * @summary generic class method with vararg gets "java.lang.AssertionError: arraycode T"
 * @author gafter
 *
 * @compile  BadSyntax2.java
 */

class Tclass<T> {
    T data;
    public Tclass(T... t){}
}

public class BadSyntax2 {
    String s = null;
    Tclass<String> tc = new Tclass<String>(s);  //this gets Assertion
    public BadSyntax2() {}
}
