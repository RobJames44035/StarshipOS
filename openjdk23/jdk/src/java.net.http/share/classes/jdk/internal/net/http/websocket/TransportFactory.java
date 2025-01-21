/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.internal.net.http.websocket;

@FunctionalInterface
public interface TransportFactory {

    Transport createTransport(MessageQueue queue,
                              MessageStreamConsumer consumer);
}
