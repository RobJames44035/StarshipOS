/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @run testng SpliteratorTraverseAddRemoveTest
 * @bug 8085978
 * @summary repeatedly traverse the queue using the spliterator while
 *          concurrently adding and removing an element to test that self-linked
 *          nodes are never erroneously reported on traversal
 */

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Spliterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Test
public class SpliteratorTraverseAddRemoveTest {

    static Object[] of(String desc, Consumer<Queue<?>> c) {
        return new Object[]{desc, c};
    }

    static void assertIsString(Object e) {
        Assert.assertTrue(e instanceof String,
                          String.format("Object instanceof %s (actual: instanceof %s)",
                                        String.class.getName(),
                                        e.getClass().getName()));
    }

    @DataProvider()
    public static Object[][] spliteratorTraversers() {
        return new Object[][]{
                of("forEachRemaining", q -> {
                    q.spliterator().forEachRemaining(SpliteratorTraverseAddRemoveTest::assertIsString);
                }),
                of("tryAdvance", q -> {
                    Spliterator<?> s = q.spliterator();
                    while (s.tryAdvance(SpliteratorTraverseAddRemoveTest::assertIsString))
                        ;
                }),
                of("trySplit then forEachRemaining", q -> {
                    Spliterator<?> r = q.spliterator();

                    List<Spliterator<?>> ss = new ArrayList<>();
                    Spliterator<?> l;
                    while ((l = r.trySplit()) != null) {
                        ss.add(l);
                    }
                    ss.add(r);

                    ss.forEach(s -> s.forEachRemaining(SpliteratorTraverseAddRemoveTest::assertIsString));
                }),
        };
    }

    @Test(dataProvider = "spliteratorTraversers")
    public void testQueue(String desc, Consumer<Queue<String>> c)
            throws InterruptedException {
        AtomicBoolean done = new AtomicBoolean(false);
        Queue<String> msgs = new LinkedTransferQueue<>();

        CompletableFuture<Void> traversalTask = CompletableFuture.runAsync(() -> {
            while (!done.get()) {
                // Traversal will fail if self-linked nodes of
                // LinkedTransferQueue are erroneously reported
                c.accept(msgs);
            }
        });
        CompletableFuture<Void> addAndRemoveTask = CompletableFuture.runAsync(() -> {
            while (!traversalTask.isDone()) {
                msgs.add("msg");
                msgs.remove("msg");
            }
        });

        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        done.set(true);

        addAndRemoveTask.join();
        Assert.assertTrue(traversalTask.isDone());
        traversalTask.join();
    }
}
