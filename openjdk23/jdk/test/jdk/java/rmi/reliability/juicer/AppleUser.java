/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * AppleUser is a remote interface that allows the application server
 * (a generator of Apple objects) to pass Apple objects and to
 * communicate various conditions to the implementation of this interface.
 */
public interface AppleUser extends Remote {
    void startTest() throws RemoteException;
    void reportException(Exception status) throws RemoteException;
    void useApple(Apple apple) throws RemoteException;
}
