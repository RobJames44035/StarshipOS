/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import jdk.test.lib.util.FileUtils;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

import static org.testng.Assert.assertEquals;

/*
 * @test
 * @summary Verifies that some of the standard BodyPublishers relay exception
 *          rather than throw it
 * @bug 8226303
 * @library /test/lib
 * @run testng/othervm RelayingPublishers
 */
public class RelayingPublishers {

    @Test
    public void ofFile0() throws IOException {
        Path directory = Files.createDirectory(Path.of("d"));
        // Even though the path exists, the publisher should not be able
        // to read from it, as that path denotes a directory, not a file
        BodyPublisher pub = BodyPublishers.ofFile(directory);
        CompletableSubscriber<ByteBuffer> s = new CompletableSubscriber<>();
        pub.subscribe(s);
        s.future().join();
        // Interestingly enough, it's FileNotFoundException if a file
        // is a directory
        assertEquals(s.future().join().getClass(), FileNotFoundException.class);
    }

    @Test
    public void ofFile1() throws IOException {
        Path file = Files.createFile(Path.of("f"));
        BodyPublisher pub = BodyPublishers.ofFile(file);
        FileUtils.deleteFileWithRetry(file);
        CompletableSubscriber<ByteBuffer> s = new CompletableSubscriber<>();
        pub.subscribe(s);
        assertEquals(s.future().join().getClass(), FileNotFoundException.class);
    }

    @Test
    public void ofByteArrays() {
        List<byte[]> bytes = new ArrayList<>();
        bytes.add(null);
        BodyPublisher pub = BodyPublishers.ofByteArrays(bytes);
        CompletableSubscriber<ByteBuffer> s = new CompletableSubscriber<>();
        pub.subscribe(s);
        assertEquals(s.future().join().getClass(), NullPointerException.class);
    }

    static class CompletableSubscriber<T> implements Flow.Subscriber<T> {

        final CompletableFuture<Throwable> f = new CompletableFuture<>();

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            f.completeExceptionally(new RuntimeException("Unexpected onNext"));
        }

        @Override
        public void onError(Throwable throwable) {
            f.complete(throwable);
        }

        @Override
        public void onComplete() {
            f.completeExceptionally(new RuntimeException("Unexpected onNext"));
        }

        CompletableFuture<Throwable> future() {
            return f.copy();
        }
    }
}
