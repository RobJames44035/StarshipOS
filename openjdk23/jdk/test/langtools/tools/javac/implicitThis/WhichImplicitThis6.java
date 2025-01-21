/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4717633
 * @summary compiler fails to allow access to enclosing instance in super()
 *
 * @compile WhichImplicitThis6.java
 */

class WhichImplicitThis6 {
    private int i;
    WhichImplicitThis6(int i) {}
    class Sub extends WhichImplicitThis6 {
        Sub() {
            super(i); // i is not inherited, so it is the enclosing i
        }
    }
}
