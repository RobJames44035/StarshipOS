/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 *
 */

package bench.rmi;

import bench.Benchmark;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Benchmark for testing speed of calls with remote object array parameters and
 * return values.
 */
public class RemoteObjArrayCalls implements Benchmark {

    static class RemoteObj extends UnicastRemoteObject implements Remote {
        RemoteObj() throws RemoteException {
        }
    }

    interface Server extends Remote {
        public Remote[] call(Remote[] a) throws RemoteException;
    }

    static class ServerImpl extends UnicastRemoteObject implements Server {
        public ServerImpl() throws RemoteException {
        }

        public Remote[] call(Remote[] a) throws RemoteException {
            return a;
        }
    }

    static class ServerFactory implements BenchServer.RemoteObjectFactory {
        public Remote create() throws RemoteException {
            return new ServerImpl();
        }
    }

    /**
     * Issue calls using arrays of remote objects as parameters/return values.
     * Arguments: <array size> <# calls>
     */
    public long run(String[] args) throws Exception {
        int size = Integer.parseInt(args[0]);
        int reps = Integer.parseInt(args[1]);
        BenchServer bsrv = Main.getBenchServer();
        Server stub = (Server) bsrv.create(new ServerFactory());
        Remote[] objs = new Remote[size];
        for (int i = 0; i < size; i++)
            objs[i] = new RemoteObj();

        long start = System.currentTimeMillis();
        for (int i = 0; i < reps; i++)
            stub.call(objs);
        long time = System.currentTimeMillis() - start;

        bsrv.unexport(stub, true);
        return time;
    }
}
