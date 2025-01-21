/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug     8129214
 * @summary Access error when unboxing a primitive whose target is a type-variable in a different package
 * @compile T8129214.java
 */
import pkg.Foo;

class T8129214 {
    void test() {
        Foo.foo(10);
    }
}
