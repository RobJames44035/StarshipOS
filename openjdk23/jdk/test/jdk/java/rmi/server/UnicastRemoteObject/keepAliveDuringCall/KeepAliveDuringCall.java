/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4308492
 * @summary In addition to keeping the VM alive (with a non-daeman thread)
 * while there are remote objects exported, the RMI runtime should also
 * keep it alive while there remain calls in progress (to remote objects
 * the have presumably been unexported), so that a remote object can more
 * conveniently implement a graceful remote shutdown method (that unexports
 * the object).
 * @author Peter Jones
 *
 * @library ../../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary JavaVM KeepAliveDuringCall_Stub
 *     ShutdownMonitor Shutdown ShutdownImpl ShutdownImpl_Stub
 * @run main/othervm KeepAliveDuringCall
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class KeepAliveDuringCall implements ShutdownMonitor {

    public static final String BINDING = "KeepAliveDuringCall";
    private static final int TIMEOUT = 20000;

    private Object lock = new Object();
    private Shutdown shutdown = null;
    private boolean stillAlive = false;

    public void submitShutdown(Shutdown shutdown) {
        synchronized (lock) {
            this.shutdown = shutdown;
            lock.notifyAll();
        }
    }

    public void declareStillAlive() {
        synchronized (lock) {
            stillAlive = true;
            lock.notifyAll();
        }
    }

    public static void main(String[] args) {

        System.err.println("\nRegression test for bug 4308492\n");

        KeepAliveDuringCall obj = new KeepAliveDuringCall();
        JavaVM jvm = null;

        try {
            UnicastRemoteObject.exportObject(obj);
            System.err.println("exported shutdown monitor");

            Registry localRegistry = TestLibrary.createRegistryOnEphemeralPort();
            int registryPort = TestLibrary.getRegistryPort(localRegistry);
            System.err.println("created local registry");

            localRegistry.bind(BINDING, obj);
            System.err.println("bound shutdown monitor in local registry");

            System.err.println("starting remote ShutdownImpl VM...");
            jvm = new JavaVM("ShutdownImpl",
                        "-Drmi.registry.port=" +
                        registryPort, "");
            jvm.start();

            Shutdown s;
            synchronized (obj.lock) {
                System.err.println(
                    "waiting for submission of object to shutdown...");
                while ((s = obj.shutdown) == null) {
                    obj.lock.wait(TIMEOUT);
                }
                if (s == null) {
                    throw new RuntimeException(
                        "TEST FAILED: timeout waiting for shutdown object " +
                        "to make initial contact");
                }
                System.err.println("shutdown object submitted: " + s);
            }

            try {
                s.shutdown();
            } catch (RemoteException e) {
                throw new RuntimeException(
                    "TEST FAILED: shutdown method threw remote exception", e);
            }

            synchronized (obj.lock) {
                if (!obj.stillAlive) {
                    throw new RuntimeException("TEST FAILED: " +
                        "shutdown object not detected alive after unexport");
                }
            }

            System.err.println("TEST PASSED: " +
                "shutdown object detected still alive after unexport");

        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(
                    "TEST FAILED: unexpected exception", e);
            }
        } finally {
            if (jvm != null) {
                jvm.destroy();
            }
            try {
                UnicastRemoteObject.unexportObject(obj, true);
            } catch (RemoteException e) {
            }
        }
    }
}
