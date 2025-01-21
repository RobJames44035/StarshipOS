/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4680662
 * @summary InternalError using inner classes in static { } block
 * @author gafter
 *
 * @compile StaticBlockScope.java
 */

class StaticBlockScope {

    static {
        Object A = new Object () {
                Object B = C;
            };
    }

    static final Object C
        = new Object () {
                Object D = null;
            };

}
