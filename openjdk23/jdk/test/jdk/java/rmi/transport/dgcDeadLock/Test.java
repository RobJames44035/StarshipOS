/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.rmi.*;

public interface Test extends Remote {
    String echo(String msg) throws RemoteException;
}
