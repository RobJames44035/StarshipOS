/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/* @test
 * @bug 6269166
 * @summary After a remote object has been exported on an anonymous
 * port, it should be possible to export another remote object on an
 * explicit port (and with the same socket factories, if any) with the
 * same value as the actual port to which the first export got bound.
 * While the fact that this works (instead of failing with a
 * BindException) might seem odd and not to be expected, it has
 * historically worked with the J2SE RMI implementation, so it should
 * continue to work because existing applications might depend on it.
 * @author Peter Jones
 *
 * @library ../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary
 * @run main/othervm ReuseDefaultPort
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ReuseDefaultPort implements Remote {

    private static int rmiPort = 0;

    private ReuseDefaultPort() { }

    public static void main(String[] args) throws Exception {
        System.err.println("\nRegression test for bug 6269166\n");
        RMISocketFactory.setSocketFactory(new SF());
        Remote impl = new ReuseDefaultPort();
        Remote stub = UnicastRemoteObject.exportObject(impl, 0);
        System.err.println("- exported object: " + stub);
        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            System.err.println("- exported registry: " + registry);
            System.err.println("TEST PASSED");
        } finally {
            UnicastRemoteObject.unexportObject(impl, true);
        }
    }

    private static class SF extends RMISocketFactory {
        private static RMISocketFactory defaultFactory =
            RMISocketFactory.getDefaultSocketFactory();
        SF() { }
        public Socket createSocket(String host, int port) throws IOException {
            System.err.format("in SF::createSocket: %s, %d%n", host, port);
            return defaultFactory.createSocket(host, port);
        }
        public ServerSocket createServerSocket(int port) throws IOException {
            System.err.format("in SF::createServerSocket: %d%n", port);
            ServerSocket server = defaultFactory.createServerSocket(port);
            rmiPort = server.getLocalPort();
            System.err.println("rmiPort: " + rmiPort);
            return server;
        }
    }
}
