/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.rmi.*;

public interface TestIface
    extends Remote
{
    public String testCall(String ign)
        throws RemoteException;
}
