/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @library /test/lib
 * @requires (os.family == "linux" | os.family == "mac" | os.family == "windows")
 * @bug 8209152
 * @run main PrintSupportedOptions
 * @run main/othervm -Djava.net.preferIPv4Stack=true PrintSupportedOptions
 */

import java.io.IOException;
import java.net.SocketOption;
import java.nio.channels.*;
import java.util.*;

import jdk.test.lib.net.IPSupport;

public class PrintSupportedOptions {

    @FunctionalInterface
    interface NetworkChannelSupplier<T extends NetworkChannel> {
        T get() throws IOException;
    }

    public static void main(String[] args) throws IOException {
        IPSupport.throwSkippedExceptionIfNonOperational();

        test(() -> SocketChannel.open());
        test(() -> ServerSocketChannel.open());
        test(() -> DatagramChannel.open());

        test(() -> AsynchronousSocketChannel.open());
        test(() -> AsynchronousServerSocketChannel.open());
    }

    static final Set<String> READ_ONLY_OPTS = Set.of("SO_INCOMING_NAPI_ID");

    @SuppressWarnings("unchecked")
    static <T extends NetworkChannel>
    void test(NetworkChannelSupplier<T> supplier) throws IOException {
        try (T ch = supplier.get()) {
            System.out.println(ch);
            for (SocketOption<?> opt : ch.supportedOptions()) {
                Object value = ch.getOption(opt);
                System.out.format(" %s -> %s%n", opt.name(), value);
                if (!READ_ONLY_OPTS.contains(opt.name())) {
                    if (value != null)
                        ch.setOption((SocketOption<Object>) opt, value);
                }
            }
        }
    }
}
