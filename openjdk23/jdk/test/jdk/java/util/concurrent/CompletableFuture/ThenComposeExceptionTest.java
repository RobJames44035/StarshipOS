/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @test
 * @bug 8068432 8072030
 * @run testng ThenComposeExceptionTest
 * @summary Test that CompletableFuture.thenCompose works correctly if the
 * composing future completes exceptionally
 */
@Test
public class ThenComposeExceptionTest {

    static final BiFunction<CompletableFuture<String>, CompletableFuture<String>, CompletableFuture<String>>
            THEN_COMPOSE = (f, fe) -> f.thenCompose(s -> fe);

    static final BiFunction<CompletableFuture<String>, CompletableFuture<String>, CompletableFuture<String>>
            THEN_COMPOSE_ASYNC = (f, fe) -> f.thenComposeAsync(s -> fe);

    static final Consumer<CompletableFuture<String>>
            COMPLETE_EXCEPTIONALLY = f -> f.completeExceptionally(new RuntimeException());

    static final Consumer<CompletableFuture<String>>
            NOP = f -> { };

    static Object[][] actionsDataProvider;

    @DataProvider(name = "actions")
    static Object[][] actionsDataProvider() {
        if (actionsDataProvider != null) {
            return actionsDataProvider;
        }

        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"thenCompose and completeExceptionally", NOP, THEN_COMPOSE, COMPLETE_EXCEPTIONALLY});
        data.add(new Object[]{"thenComposeAsync and completeExceptionally", NOP, THEN_COMPOSE_ASYNC, COMPLETE_EXCEPTIONALLY});
        data.add(new Object[]{"completeExceptionally and thenCompose", COMPLETE_EXCEPTIONALLY, THEN_COMPOSE, NOP});
        data.add(new Object[]{"completeExceptionally and thenComposeAsync", COMPLETE_EXCEPTIONALLY, THEN_COMPOSE_ASYNC, NOP});

        return actionsDataProvider = data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "actions")
    public void testThenCompose(
            String description,
            Consumer<CompletableFuture<String>> beforeAction,
            BiFunction<CompletableFuture<String>, CompletableFuture<String>, CompletableFuture<String>> composeFunction,
            Consumer<CompletableFuture<String>> afterAction) throws Exception {
        CompletableFuture<String> f = new CompletableFuture<>();
        CompletableFuture<String> fe = new CompletableFuture<>();

        // Ensure pre-composed stage is completed to trigger
        // processing the composing future
        f.complete("");

        beforeAction.accept(fe);

        CompletableFuture<String> f_thenCompose = composeFunction.apply(f, fe);
        Assert.assertNotSame(f_thenCompose, fe, "Composed CompletableFuture returned directly");

        AtomicReference<Throwable> eOnWhenComplete = new AtomicReference<>();
        CompletableFuture<String> f_whenComplete = f_thenCompose.
                whenComplete((r, e) -> eOnWhenComplete.set(e));

        afterAction.accept(fe);

        Throwable eOnJoined = null;
        try {
            f_thenCompose.join();
        }
        catch (Throwable t) {
            eOnJoined = t;
        }
        Assert.assertTrue(eOnJoined instanceof CompletionException,
                          "Incorrect exception reported when joined on thenCompose: " + eOnJoined);

        // Need to wait for f_whenComplete to complete to avoid
        // race condition when updating eOnWhenComplete
        eOnJoined = null;
        try {
            f_whenComplete.join();
        } catch (Throwable t) {
            eOnJoined = t;
        }
        Assert.assertTrue(eOnJoined instanceof CompletionException,
                          "Incorrect exception reported when joined on whenComplete: " + eOnJoined);
        Assert.assertTrue(eOnWhenComplete.get() instanceof CompletionException,
                          "Incorrect exception passed to whenComplete: " + eOnWhenComplete.get());
    }
}
