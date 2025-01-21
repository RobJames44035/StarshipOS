/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package testPkg;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    int port;

    public Client(int p) {
        port = p;
    }

    public String testStub() throws Exception {
        try {
            Registry registry = LocateRegistry.getRegistry(port);
            Hello stub = (Hello) registry.lookup("Hello");
            String response = stub.sayHello();
            return response;
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                throw e;
            }
        }
    }

