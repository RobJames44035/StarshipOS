/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 *
 */

package bench.rmi;

import bench.Benchmark;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Benchmark for testing speed of calls with small object tree parameters and
 * return values.
 */
public class SmallObjTreeCalls implements Benchmark {

    static class Node implements Serializable {
        Object parent, left, right;

        Node(Object parent, int depth) {
            this.parent = parent;
            if (depth > 0) {
                left = new Node(this, depth - 1);
                right = new Node(this, depth - 1);
            }
        }
    }

    interface Server extends Remote {
        public Node call(Node val) throws RemoteException;
    }

    static class ServerImpl extends UnicastRemoteObject implements Server {
        public ServerImpl() throws RemoteException {
        }

        public Node call(Node val) throws RemoteException {
            return val;
        }
    }

    static class ServerFactory implements BenchServer.RemoteObjectFactory {
        public Remote create() throws RemoteException {
            return new ServerImpl();
        }
    }

    /**
     * Issue calls using trees of small objects as parameters/return values.
     * Arguments: <tree depth> <# calls>
     */
    public long run(String[] args) throws Exception {
        int depth = Integer.parseInt(args[0]);
        int reps = Integer.parseInt(args[1]);
        BenchServer bsrv = Main.getBenchServer();
        Server stub = (Server) bsrv.create(new ServerFactory());
        Node node = new Node(null, depth);

        long start = System.currentTimeMillis();
        for (int i = 0; i < reps; i++)
            stub.call(node);
        long time = System.currentTimeMillis() - start;

        bsrv.unexport(stub, true);
        return time;
    }
}
