/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**/

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

/**
 * Class to run a rmiregistry whose VM can be told to exit remotely;
 * Difference between this class and RegistryRunner is that this class
 * simulate rmiregistry closer than RegistryRunner.
 */
public class RMIRegistryRunner extends RegistryRunner
{
    public RMIRegistryRunner() throws RemoteException {
    }

    /**
     * port 0 means to use ephemeral port to start registry.
     *
     * @param args command line arguments passed in from main
     * @return the port number on which registry accepts requests
     */
    protected static int init(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Usage: <port>");
                System.exit(0);
            }
            int port = -1;
            port = Integer.parseInt(args[0]);

            // call RegistryImpl.createRegistry to simulate rmiregistry.
            registry = sun.rmi.registry.RegistryImpl.createRegistry(port);
            if (port == 0) {
                port = TestLibrary.getRegistryPort(registry);
            }

            // create a remote object to tell this VM to exit
            exiter = new RMIRegistryRunner();
            Naming.rebind("rmi://localhost:" + port +
                          "/RemoteExiter", exiter);

            return port;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return -1;
    }

    public static void main(String[] args) {
        int port = init(args);
        notify(port);
    }
}
