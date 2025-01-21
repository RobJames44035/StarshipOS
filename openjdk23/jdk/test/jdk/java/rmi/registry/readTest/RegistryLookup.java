/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegistryLookup {
    public static final int EXIT_FAIL = 1;

    public static void main(String args[]) throws Exception {
        Registry registry = null;
        int exit = 0;
        try {
            int port = Integer.valueOf(args[0]);

            testPkg.Server obj = new testPkg.Server();
            testPkg.Hello stub =
                    (testPkg.Hello) UnicastRemoteObject.exportObject(obj, 0);
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.getRegistry(port);
            registry.bind("Hello", stub);
            System.err.println("Server ready");

            testPkg.Client client = new testPkg.Client(port);
            String testStubReturn = client.testStub();
            if(!testStubReturn.equals(obj.hello)) {
                throw new RuntimeException("Test Fails : "
                        + "unexpected string from stub call");
            }
            registry.unbind("Hello");
            System.out.println("Test passed");
        } catch (Exception ex) {
            exit = EXIT_FAIL;
            ex.printStackTrace();
        }
        // need to exit explicitly, and parent process uses exit value
        // to tell if the test passed.
        System.exit(exit);
    }
}
