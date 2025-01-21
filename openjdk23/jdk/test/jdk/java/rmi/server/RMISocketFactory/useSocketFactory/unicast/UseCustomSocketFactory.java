/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4127826
 *
 * @summary synopsis: need to download factories for use with custom socket
 * types
 * @author Ann Wollrath
 *
 * @library ../../../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary JavaVM Echo EchoImpl EchoImpl_Stub
 * @run main/othervm/timeout=120 UseCustomSocketFactory
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;

public class UseCustomSocketFactory {

    public static void main(String[] args) {

        int registryPort = -1;

        String[] protocols = new String[] { "", "compress", "xor" };

        System.out.println("\nRegression test for bug 4127826\n");

        try {
            Registry registry = TestLibrary.createRegistryOnEphemeralPort();
            registryPort = TestLibrary.getRegistryPort(registry);
        } catch (RemoteException e) {
            TestLibrary.bomb("creating registry", e);
        }
        for (String protocol : protocols) {
            JavaVM serverVM = new JavaVM("EchoImpl",
                    " -Drmi.registry.port=" +
                    registryPort, protocol);
            System.err.println("\nusing protocol: " +
                    ("".equals(protocol) ? "none" : protocol));
            try {
                /* spawn VM for EchoServer */
                serverVM.start();

                /* lookup server */
                Echo obj = null;
                // 16 seconds timeout
                long stopTime = System.currentTimeMillis() + 16000;
                do {
                    try {
                        obj = (Echo) Naming.lookup("//:" + registryPort +
                                                   "/EchoServer");
                        break;
                    } catch (NotBoundException e) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ignore) {
                        }
                    }
                } while (System.currentTimeMillis() < stopTime);

                if (obj == null)
                    TestLibrary.bomb("server not bound in 8 tries", null);

                /* invoke remote method and print result*/
                System.err.println("Bound to " + obj);
                byte[] data = ("Greetings, citizen " +
                        System.getProperty("user.name") + "!"). getBytes();
                byte[] result = obj.echoNot(data);
                for (int j = 0; j < result.length; j++)
                    result[j] = (byte) ~result[j];
                System.err.println("Result: " + new String(result));

            } catch (IOException e) {
                TestLibrary.bomb("test failed", e);

            } finally {
                serverVM.destroy();
                try {
                    Naming.unbind("//:" + registryPort +
                            "/EchoServer");
                } catch (RemoteException | NotBoundException | MalformedURLException e) {
                    TestLibrary.bomb("unbinding EchoServer", e);

                }
            }
        }
    }
}
