/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/*
 * A custom socket factory used to override the default socket factory and track the LDAP client connection.
 * Factory can create only one SSLSocket. See the DeadServerTimeoutSSLTest test.
 */
public class DeadSSLSocketFactory extends SocketFactory {
    // Client socket that is used by LDAP connection
    public static AtomicReference<SSLSocket> firstCreatedSocket = new AtomicReference<>();

    // Boolean to track if connection socket has been opened
    public static AtomicBoolean isConnectionOpened = new AtomicBoolean(false);

    // Default SSLSocketFactory that will be used for SSL socket creation
    final SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();

    // Create unconnected socket
    public Socket createSocket() throws IOException {
        if (!isConnectionOpened.getAndSet(true)) {
            System.err.println("DeadSSLSocketFactory: Creating unconnected socket");
            firstCreatedSocket.set((SSLSocket) factory.createSocket());
            return firstCreatedSocket.get();
        } else {
            throw new RuntimeException("DeadSSLSocketFactory only allows creation of one SSL socket");
        }
    }

    public DeadSSLSocketFactory() {
        System.err.println("DeadSSLSocketFactory: Constructor call");
    }

    public static SocketFactory getDefault() {
        System.err.println("DeadSSLSocketFactory: acquiring DeadSSLSocketFactory as default socket factory");
        return new DeadSSLSocketFactory();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        // Not used by DeadSSLLdapTimeoutTest
        return factory.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost,
                               int localPort) throws IOException {
        // Not used by DeadSSLLdapTimeoutTest
        return factory.createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        // Not used by DeadSSLLdapTimeoutTest
        return factory.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port,
                               InetAddress localAddress, int localPort) throws IOException {
        // Not used by DeadSSLLdapTimeoutTest
        return factory.createSocket(address, port, localAddress, localPort);
    }
}

