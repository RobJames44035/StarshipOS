/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ShutdownMonitor extends Remote {
    void submitShutdown(Shutdown shutdown) throws RemoteException;
    void declareStillAlive() throws RemoteException;
}
