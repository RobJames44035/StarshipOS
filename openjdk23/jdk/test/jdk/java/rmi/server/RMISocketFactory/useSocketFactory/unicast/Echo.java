/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 */

import java.rmi.*;

public interface Echo extends Remote {

    byte[] echoNot(byte[] data) throws RemoteException;
}
