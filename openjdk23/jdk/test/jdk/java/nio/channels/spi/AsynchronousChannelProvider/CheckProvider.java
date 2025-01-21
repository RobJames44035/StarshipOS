/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary Unit test for java.nio.channels.spi.AsynchronousChannelProvider
 * @build Provider1 Provider2
 * @run main/othervm CheckProvider Provider1
 * @run main/othervm -Djava.nio.channels.spi.AsynchronousChannelProvider=Provider2
 *                   CheckProvider Provider2
 */

import java.nio.channels.spi.AsynchronousChannelProvider;

public class CheckProvider {
    public static void main(String[] args) {
        Class<?> c = AsynchronousChannelProvider.provider().getClass();

        String expected = args[0];
        String actual = c.getName();

        if (!actual.equals(expected))
            throw new RuntimeException("Provider is of type '" + actual +
                "', expected '" + expected + "'");

    }
}
