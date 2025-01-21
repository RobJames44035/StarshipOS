/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.rmi.*;

interface LeaseLeak extends Remote {

    void ping() throws RemoteException;
}
