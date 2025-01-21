/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8213908
 * @summary AssertionError in DeferredAttr at setOverloadKind
 * @compile MethodRefStuck2.java
 */

import java.util.Optional;
import java.util.stream.Stream;

public abstract class MethodRefStuck2 {

    abstract void f(long c);

    interface I {
        I g(String o);
    }

    private void test(Stream<I> is, Optional<String> o) {
        f(
                is.map(
                                i -> {
                                    o.ifPresent(i::g);
                                    return null;
                                })
                        .count());
    }
}
