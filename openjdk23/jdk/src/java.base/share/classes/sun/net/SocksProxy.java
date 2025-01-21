/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.net;

import java.net.Proxy;
import java.net.SocketAddress;

/**
 * Proxy wrapper class so we can determine the socks protocol version.
 */
public final class SocksProxy extends Proxy {
    private final int version;

    private SocksProxy(SocketAddress addr, int version) {
        super(Proxy.Type.SOCKS, addr);
        this.version = version;
    }

    public static SocksProxy create(SocketAddress addr, int version) {
        return new SocksProxy(addr, version);
    }

    public int protocolVersion() {
        return version;
    }
}
