/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4916665
 * @summary when upper bounds and lower bounds collide
 * @author gafter
 *
 * @compile  -Werror BoundsCollision.java
 */

class StreinBug {

    static class Box<T extends Number> {
        void f(Box<T> bt) { }
    }

    public void g() {
        Box<? super Number> b0 = null;
        Box<Number> b1 = b0;
        b0.f(b1); // <<pass>>
    }

}
