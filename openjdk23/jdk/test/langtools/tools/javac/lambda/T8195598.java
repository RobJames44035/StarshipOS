/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8195598
 * @summary Reference to overloaded method is ambiguous with 3 methods but works with 2
 * @compile T8195598.java
 */

import java.util.concurrent.*;
import java.util.function.*;

class T8195598 {
    public CompletableFuture<?> test() {
        return ok(() -> System.out.append("aaa"));
    }
    public <T> CompletableFuture<T> ok(Supplier<T> action) {
        return CompletableFuture.supplyAsync(action);
    }
    public <T> CompletableFuture<T> ok(T body) {
        return CompletableFuture.completedFuture(body);
    }
    public CompletableFuture<Void> ok(Runnable action) {
        return CompletableFuture.runAsync(action);
    }
}
