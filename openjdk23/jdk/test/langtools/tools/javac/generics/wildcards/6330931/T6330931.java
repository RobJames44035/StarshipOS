/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6330931
 * @summary Super wildcard has incorrect upper bound
 * @author  Peter von der Ah\u00e9
 * @compile T6330931.java
 */

import java.util.List;

class Foo {}
class Bar extends Foo {}
interface FooList<T extends Foo> extends List<T> {}

class Test {
    <T extends FooList<? super Bar>> void m(T t) {
        Foo f = t.get(0);
    }
}
