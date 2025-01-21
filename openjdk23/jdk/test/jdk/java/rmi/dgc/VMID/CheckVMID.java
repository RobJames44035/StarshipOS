/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4171370
 * @summary VMIDs should be unique regardless of whether
 * an IP address can be obtained.  Instead of using an IP
 * address in a VMID, a secure hash (using SHA) of the IP
 * address is used.
 * @author Ann Wollrath
 *
 * @library ../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary
 * @run main/othervm -Djdk.net.hosts.file=nonExistentFile
 *                   CheckVMID
 */

import java.rmi.dgc.VMID;
import java.net.InetAddress;

public class CheckVMID {

    public static void main(String[] args) {

        System.err.println("\nRegression test for bug 4171370\n");

        try {
            System.err.println("Create a VMID");
            VMID vmid = new VMID();
            System.err.println("vmid = " + vmid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("TEST FAILED: " + e.toString());
        }
    }
}
