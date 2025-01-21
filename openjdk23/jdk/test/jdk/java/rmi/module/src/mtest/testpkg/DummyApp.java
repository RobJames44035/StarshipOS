/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package testpkg;

import java.rmi.server.UnicastRemoteObject;

import clientpkg.Client;
import serverpkg.Hello;
import serverpkg.Server;

public class DummyApp {

    public static void main(String args[]) {
        try {
            Hello obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            Client client = new Client(stub);
            String testStubReturn = client.testStub();
            System.out.println("Stub is: " + testStubReturn);
            if (!testStubReturn.equals(obj.sayHello())) {
                throw new RuntimeException("Unexpected string from stub call, expected \""
                        + testStubReturn + "\", actual \"" + obj.sayHello() + "\"");
            } else {
                System.out.println("Test passed");
            }

            System.exit(0);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
