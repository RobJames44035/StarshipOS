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
 * Benchmark for testing speed of calls with int array arguments and
 * return values.
 */
public class IntArrayCalls implements Benchmark {

    interface Server extends Remote {
        public int[] call(int[] a) throws RemoteException;
    }

    static class ServerImpl extends UnicastRemoteObject implements Server {
        public ServerImpl() throws RemoteException {
        }

        public int[] call(int[] a) throws RemoteException {
            return a;
        }
    }

    static class ServerFactory implements BenchServer.RemoteObjectFactory {
        public Remote create() throws RemoteException {
            return new ServerImpl();
        }
    }

    /**
     * Issue int array calls.
     * Arguments: <array size> <# calls>
     */
    public long run(String[] args) throws Exception {
        int size = Integer.parseInt(args[0]);
        int reps = Integer.parseInt(args[1]);
        BenchServer bsrv = Main.getBenchServer();
        Server stub = (Server) bsrv.create(new ServerFactory());
        int[] array = new int[size];

        long start = System.currentTimeMillis();
        for (int i = 0; i < reps; i++)
            stub.call(array);
        long time = System.currentTimeMillis() - start;

        bsrv.unexport(stub, true);
        return time;
    }
}
