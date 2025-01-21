/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */


import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;

/* @test
 * @bug 8237368
 * @summary Allow custom socket factory to be null in TCPEndpoint.
 * @run main/othervm TCPEndpointReadBug
 */
public class TCPEndpointReadBug {

    public static void main(String[] args) throws Exception {
        final I implC = new C();
        final I remoteC = (I)UnicastRemoteObject.exportObject(
                implC, 0, new CSF(), RMISocketFactory.getDefaultSocketFactory());

        // Pass a remote object with a custom socket factory as an argument
        remoteC.echo(remoteC);

        // Pass nothing and get an object with a custom socket factory in return
        remoteC.echo(null);
    }

    interface I extends Remote {
        I echo(I intf) throws RemoteException;
    }

    static class C implements I {
        @Override
        public I echo(I intf) {
            try {
                return  (I)UnicastRemoteObject
                    .exportObject(new C(),0, new CSF(), RMISocketFactory.getDefaultSocketFactory());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * A configurable socket factory in which for test purposes supplies null.
     */
    static class CSF implements Serializable, RMIClientSocketFactory {
        private static final long serialVersionUID = 1;

        @Override
        public boolean equals(Object object) {
            return object instanceof CSF;
        }

        @Override
        public int hashCode() {
            return 424242;
        }

        @Override
        public Socket createSocket(String host, int port)
            throws IOException {

            final RMIClientSocketFactory defaultFactory =
                RMISocketFactory.getDefaultSocketFactory();
            return defaultFactory.createSocket(host, port);
        }

        /**
         * Use writeReplace to use a different client socket factory. In the
         * problematic case, the replacement is null.
         */
        private Object writeReplace() {
            return null;
        }

        /**
         * Instances of this class should never be deserialized because they
         * are always replaced during serialization.
         */
        @SuppressWarnings("unused")
        private void readObject(ObjectInputStream in) {
            throw new AssertionError();
        }
    }
}
