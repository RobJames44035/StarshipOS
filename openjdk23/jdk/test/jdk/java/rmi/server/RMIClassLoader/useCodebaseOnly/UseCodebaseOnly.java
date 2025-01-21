/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4174006
 * @summary If the "java.rmi.server.useCodebaseOnly" property has a boolean
 * value of true, then when the RMI runtime is unmarshalling a class
 * descriptor, it should not attempt to download the associate Class object
 * from the annotated codebase, but only from the codebase specified in the
 * "java.rmi.server.codebase" property and the context class loader.
 * @author Peter Jones
 *
 * @library ../../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary Receiver UseCodebaseOnly_Stub Foo Bar TestLoaderHandler
 * @run main/othervm UseCodebaseOnly
 */

import java.net.*;
import java.rmi.*;
import java.rmi.server.*;

public class UseCodebaseOnly
    extends UnicastRemoteObject
    implements Receiver
{

    public UseCodebaseOnly() throws RemoteException {
    }

    public void receive(Object obj) {
        System.err.println("+ receive(): received object " + obj);
    }

    public static void main(String[] args) {

        System.err.println("\nRegression test for bug 4174006\n");

        System.setProperty("java.rmi.server.RMIClassLoaderSpi", "TestLoaderHandler");

        URL localCodebase = null, remoteCodebase = null;
        try {
            remoteCodebase =
                TestLibrary.installClassInCodebase("Foo", "remote_codebase");
            localCodebase =
                TestLibrary.installClassInCodebase("Bar", "local_codebase");
        } catch (MalformedURLException e) {
            TestLibrary.bomb(e);
        }

        TestLibrary.setProperty("java.rmi.server.useCodebaseOnly", "true");
        TestLibrary.setProperty(        // set local codebase property
            "java.rmi.server.codebase", localCodebase.toString());

        /*
         * Load Foo and Bar from non-RMI class loaders so that they won't be
         * already loaded by RMI class loaders in this VM (for whatever that's
         * worth), but with URLClassLoader so that they will be annotated
         * properly.
         */
        System.err.println("Creating class loader for remote codebase " +
            remoteCodebase);
        ClassLoader remoteCodebaseLoader =
            URLClassLoader.newInstance(new URL[] { remoteCodebase });
        System.err.println("Creating class loader for local codebase " +
            localCodebase);
        ClassLoader localCodebaseLoader =
            URLClassLoader.newInstance(new URL[] { localCodebase });

        System.err.println("Creating remote object.");
        UseCodebaseOnly obj = null;
        try {
            obj = new UseCodebaseOnly();
        } catch (RemoteException e) {
            TestLibrary.bomb(e);
        }

        try {
            Receiver stub = (Receiver) RemoteObject.toStub(obj);

            /*
             * Pass an instance of Bar, the class in the local codebase.
             * This should succeed, because the server is allow to load
             * classes from the local codebase.
             */
            System.err.println(
                "Passing class from local codebase (should succeed).");
            Class barClass = localCodebaseLoader.loadClass("Bar");
            Object barObj = barClass.newInstance();
            stub.receive(barObj);

            /*
             * Pass an instance of Foo, the class in the remote codebase.
             * This should fail, because the server is not allowed to load
             * classes from other codebases besides the local codebase.
             */
            System.err.println(
                "Passing class from remote codebase (should fail).");
            Class fooClass = remoteCodebaseLoader.loadClass("Foo");
            Object fooObj = fooClass.newInstance();
            try {
                stub.receive(fooObj);
                throw new RuntimeException("TEST FAILED: " +
                    "class from remote codebase sucesssfully unmarshalled");
            } catch (RemoteException e) {
                /*
                 * Verify that the failure was that the server couldn't
                 * unmarshal the Foo class.
                 */
                if ((e instanceof ServerException) &&
                    (e.detail instanceof UnmarshalException) &&
                    (((RemoteException) e.detail).detail instanceof
                        ClassNotFoundException) &&
                    (((RemoteException) e.detail).detail.getMessage().equals(
                        "Foo")))
                {
                    System.err.println("TEST PASSED: ");
                    e.printStackTrace();
                } else {
                    throw e;
                }
            }
        } catch (Exception e) {
            System.err.println("TEST FAILED: ");
            e.printStackTrace();
            throw new RuntimeException("TEST FAILED: " + e.toString());
        } finally {
            try {
                UnicastRemoteObject.unexportObject(obj, true);
            } catch (Throwable e) {
            }
        }
    }
}
