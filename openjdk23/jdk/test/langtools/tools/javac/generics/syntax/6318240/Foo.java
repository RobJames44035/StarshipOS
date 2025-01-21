/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6318240
 * @summary Creation of array of inner class of an enclosing wildcard type doesn't work
 * @author  Scott Violet
 * @compile Foo.java
 */

public class Foo<T> {
    private T t;
    private Foo<?>.Inner[] inner;
    public Foo(T t) {
        this.t = t;
        inner = new Foo<?>.Inner[10];
    }
    private class Inner {}
}
