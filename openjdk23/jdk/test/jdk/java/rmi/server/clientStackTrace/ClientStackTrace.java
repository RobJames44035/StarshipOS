/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4010355
 * @summary RemoteException should have server's stack trace
 *
 * @author Laird Dornin
 *
 * @library ../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary ClientStackTrace MyRemoteObject_Stub
 * @run main/othervm/timeout=120 ClientStackTrace
 */

/*
 * This test ensures that the stack trace in a caught server side
 * remote exception contains the string "exceptionReceivedFromServer".
 */

import java.rmi.*;
import java.rmi.server.*;
import sun.rmi.transport.StreamRemoteCall;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

interface MyRemoteInterface extends Remote {
    void ping() throws RemoteException;
}

class MyRemoteObject extends UnicastRemoteObject
    implements MyRemoteInterface {

    public MyRemoteObject () throws RemoteException {}

    public void ping () throws RemoteException {
        throw new RemoteException("This is a test remote exception");
    }
}

public class ClientStackTrace {
    Object dummy = new Object();

    public static void main(String[] args) {
        Object dummy = new Object();
        MyRemoteObject myRobj = null;
        MyRemoteInterface myStub = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(bos);

            System.err.println("\nRegression test for bug 4010355\n");

            myRobj = new MyRemoteObject();

            /* cause a remote exception to occur. */
            try {
                myStub = (MyRemoteInterface) RemoteObject.toStub(myRobj);
                myStub.ping();

            } catch (RemoteException re) {
                re.printStackTrace(ps);
                String trace = bos.toString();

                if (trace.indexOf("exceptionReceivedFromServer") <0 ) {
                    throw new RuntimeException("No client stack trace on " +
                                               "thrown remote exception");
                } else {
                    System.err.println("test passed with stack trace: " +
                                       trace);
                }
            }

            deactivate(myRobj);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("test failed");
            throw new RuntimeException(e.getMessage());
        } finally {
            myRobj = null;
            myStub = null;
        }
    }

    // make sure that the remote object goes away.
    static void deactivate(RemoteServer r) {
        // make sure that the object goes away
        try {
            System.err.println("deactivating object.");
            UnicastRemoteObject.unexportObject(r, true);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
