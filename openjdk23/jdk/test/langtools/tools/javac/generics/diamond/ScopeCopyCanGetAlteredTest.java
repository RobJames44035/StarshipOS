/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8260892
 * @summary Compilation fails: lambda parameter not visible in body when generics involved
 * @compile ScopeCopyCanGetAlteredTest.java
 */

import java.util.function.Function;
import java.util.function.IntFunction;

class ScopeCopyCanGetAlteredTest {
    interface GenericOp<A> {
        <B> A apply(IntFunction<B> func1, Function<B, A> func2);
    }

    static <A> GenericOp<A> foo(IntFunction<GenericOp<A>> f) {
        return null;
    }

    static <A> GenericOp<A> bar() {
        return foo((int arg) -> new GenericOp<>() {
            @Override
            public <B> A apply(IntFunction<B> func1, Function<B, A> func2) {
                return func2.apply(func1.apply(arg));
            }
        });
    }
}
