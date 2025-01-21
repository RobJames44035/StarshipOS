/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4136563
 * @summary Naming.lookup fails to throw exception for invalid hostname
 *
 * @bug 4208801
 * @summary (1.x) java.rmi.Naming defaults to local hostname, not
 *          local IP address
 *
 * @author Laird Dornin
 *
 * @library ../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary
 * @run main/othervm InvalidName
 */

import java.net.URL;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;

public class InvalidName {
    public static void main(String[] args) {

        String testName;

        // ensure that an exception is thrown for Naming URL with '#'
        try {

            System.err.println("\nRegression test for bugs " +
                               "4136563 and 4208801\n");

            testName = new String("rmi://#/MyRMI");

            Naming.lookup(testName);
            System.err.println("no exception thrown for URL: " + testName);
            throw new RuntimeException("test failed");

        } catch (MalformedURLException e) {
            // should have received malformed URL exception
            System.err.println("Correctly received instance of " +
                               "MalformedURLException:");
            System.err.println(e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            TestLibrary.bomb(e);
        }

        // ensure a correct null pointer exception is thrown for null URL
        // ensure that a registry stub with a default hostname and one with a
        // the local host's ip address are .equals()
        try {
            String localAddress =  InetAddress.getLocalHost().getHostAddress();

            Registry registry = (Registry) Naming.lookup("rmi:///");

            if (registry.toString().indexOf(localAddress) >= 0) {
                System.err.println("verified registry endpoint contains ipaddress");
            } else {
                TestLibrary.bomb("registry endpoint does not contain ipaddress");
            }
        } catch (Exception e) {
            TestLibrary.bomb(e);
        }

        System.err.println("test passed");
    }
}
