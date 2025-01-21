/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4518927
 * @summary RMIClassLoader.getClassAnnotation() needs to specify behavior
 * for null class
 * @author Ann Wollrath
 *
 * @library ../../../testlibrary
 * @modules java.rmi/sun.rmi.registry
 *          java.rmi/sun.rmi.server
 *          java.rmi/sun.rmi.transport
 *          java.rmi/sun.rmi.transport.tcp
 * @build TestLibrary
 * @run main/othervm NullClass
 */

import java.rmi.server.RMIClassLoader;

public class NullClass {

    public static void main(String[] args) {

        System.err.println("\nRegression test for bug 4518927\n");

        try {
            System.err.println("getting class annotation for null class...");
            String annotation = RMIClassLoader.getClassAnnotation(null);
            throw new RuntimeException(
                "TEST FAILED: NullPointerException not caught!");
        } catch (NullPointerException e) {
            System.err.println("TEST PASSED: NullPointerException caught");
        } catch (Exception e) {
            TestLibrary.bomb(e);
        }
    }
}
