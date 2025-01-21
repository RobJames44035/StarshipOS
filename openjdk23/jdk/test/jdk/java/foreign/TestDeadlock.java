/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test id=Arena_allocateFrom
 * @run main/othervm/timeout=5 --enable-native-access=ALL-UNNAMED -Xlog:class+init TestDeadlock Arena
 */

/*
 * @test id=FileChannel_map
 * @run main/othervm/timeout=5 --enable-native-access=ALL-UNNAMED -Xlog:class+init TestDeadlock FileChannel
 */

import java.lang.foreign.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

public class TestDeadlock {
    public static void main(String[] args) throws Throwable {
        CountDownLatch latch = new CountDownLatch(2);

        Runnable tester = switch (args[0]) {
            case "Arena" -> () -> {
                Arena arena = Arena.global();
                arena.scope(); // init ArenaImpl
                ValueLayout.JAVA_INT.byteSize(); // init ValueLayout (and impls)
                latch.countDown();
                try {
                    latch.await();
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Access ArenaImpl -> NativeMemorySegmentImpl -> MemorySegment
                arena.allocateFrom(ValueLayout.JAVA_INT, 42);
            };
            case "FileChannel" -> () -> {
                try {
                    Arena arena = Arena.global();
                    Path p = Files.createFile(Path.of("test.out"));

                    try (FileChannel channel = FileChannel.open(p, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
                        channel.map(FileChannel.MapMode.READ_WRITE, 0, 4); // create MappedByteBuffer to initialize other things
                        latch.countDown();
                        latch.await();

                        // Access MappedMemorySegmentImpl -> MemorySegment
                        channel.map(FileChannel.MapMode.READ_WRITE, 0, 4, arena);
                    }
                } catch(InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            };
            default -> throw new IllegalArgumentException("Unknown test selection: " + args[0]);
        };

        Thread t1 = Thread.ofPlatform().start(tester);
        Thread t2 = Thread.ofPlatform().start(() -> {
            latch.countDown();
            try {
                latch.await();
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Access MemorySegment -> NativeMemorySegmentImpl
            MemorySegment.ofAddress(42);
        });

        // wait for potential deadlock

        t1.join();
        t2.join();

        // all good
    }
}
