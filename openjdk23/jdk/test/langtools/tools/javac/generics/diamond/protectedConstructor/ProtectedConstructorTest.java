/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8225559
 * @summary assertion error at TransTypes.visitApply
 * @compile ProtectedConstructorTest.java
 */

import pkg.Bar;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

class ProtectedConstructorTest {
    public void foo() {
        supply(getSupplier(new Bar<>(){}));
        CompletableFuture<List<String>> completableFuture = getCompletableFuture(getSupplier(new Bar<>(){}));
        completableFuture = getCompletableFuture(() -> getList(null, new Bar<>() {}));
    }

    static <U> Supplier<U> getSupplier(Bar<U> t) {
        return null;
    }

    static <U> void supply(Supplier<U> supplier) {}
    static <U> CompletableFuture<U> getCompletableFuture(Supplier<U> supplier) { return null; }
    <T> List<T> getList(final Supplier<List<T>> supplier, Bar<T> t) { return null; }
}
