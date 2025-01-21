/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.rmi.*;
import java.rmi.server.*;

/**
 * interface to get server name from an execed vm
 * I am using an execed vm because I need to set
 * rmi's hostname serveral times with different properties.
 */
interface TellServerName extends Remote {
    void tellServerName (String serverName) throws RemoteException;
}
