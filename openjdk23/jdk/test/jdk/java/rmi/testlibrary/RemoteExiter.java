/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 *
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RemoteExiter extends Remote {
    void exit() throws RemoteException;
}
