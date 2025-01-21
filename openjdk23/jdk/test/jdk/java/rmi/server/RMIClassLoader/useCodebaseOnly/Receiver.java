/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 *
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Receiver extends Remote {
    public void receive(Object obj) throws RemoteException;
}
