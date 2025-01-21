/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8273234
 * @summary extended 'for' with expression of type tvar causes the compiler to crash
 * @compile ExprTypeIsTypeVariableTest.java
 */

import java.util.*;

class ExprTypeIsTypeVariableTest {
    abstract class A {}

    abstract class ACD<E> implements Iterable<E> {
        public Iterator<E> iterator() {
            return null;
        }
    }

    abstract class ALD<E> extends ACD<E> implements List<E> {}

    abstract class ASP<NT extends A> extends ALD<A> {
        <P extends ASP<NT>> void foo(P prod) {
            for (A sym : prod) {}
        }
    }
}
