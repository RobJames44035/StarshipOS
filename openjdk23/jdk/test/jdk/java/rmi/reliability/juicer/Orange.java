/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents one remote party of the deep 2-party recursion implemented by
 * this RMI reliability test. An Orange instance recursively calls back
 * to it's caller, typically an OrangeEcho instance.
 * The recursion stops when it reaches a given 'level'.
 */
public interface Orange extends Remote {
    int[] recurse(OrangeEcho echo, int[] message, int level)
        throws RemoteException;
}
