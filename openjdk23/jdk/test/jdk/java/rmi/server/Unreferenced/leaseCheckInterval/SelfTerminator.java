/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 *
 */

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SelfTerminator {

    public static void main(String[] args) {
        try {
            int registryPort =
                Integer.parseInt(System.getProperty("rmi.registry.port"));
            Registry registry =
                LocateRegistry.getRegistry("", registryPort);
            Remote stub = registry.lookup(LeaseCheckInterval.BINDING);
            Runtime.getRuntime().halt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
