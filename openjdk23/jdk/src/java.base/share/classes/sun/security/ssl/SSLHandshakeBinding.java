/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.ssl;

import java.util.Map;

interface SSLHandshakeBinding {
    default SSLHandshake[] getRelatedHandshakers(
            HandshakeContext handshakeContext) {
        return new SSLHandshake[0];
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default Map.Entry<Byte, HandshakeProducer>[] getHandshakeProducers(
            HandshakeContext handshakeContext) {
        return (Map.Entry<Byte, HandshakeProducer>[])(new Map.Entry[0]);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default Map.Entry<Byte, SSLConsumer>[] getHandshakeConsumers(
            HandshakeContext handshakeContext) {
        return (Map.Entry<Byte, SSLConsumer>[])(new Map.Entry[0]);
    }
}
