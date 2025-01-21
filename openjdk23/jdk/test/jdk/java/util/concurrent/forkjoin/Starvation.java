/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 8322732
 * @summary ForkJoinPool utilizes available workers even with arbitrary task dependencies
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class Starvation {
    static final AtomicInteger count = new AtomicInteger();
    static final Callable<Void> noop = new Callable<Void>() {
            public Void call() {
                return null; }};
    static final class AwaitCount implements Callable<Void> {
        private int c;
        AwaitCount(int c) { this.c = c; }
        public Void call() {
            while (count.get() == c) Thread.onSpinWait();
            return null; }};

    public static void main(String[] args) throws Exception {
        try (var pool = new ForkJoinPool(2)) {
            for (int i = 0; i < 100_000; i++) {
                var future1 = pool.submit(new AwaitCount(i));
                var future2 = pool.submit(noop);
                future2.get();
                count.set(i + 1);
                future1.get();
            }
        }
    }
}
