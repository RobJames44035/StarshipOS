/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 */
import java.rmi.*;
import java.rmi.server.*;

public class EchoImpl
    extends UnicastRemoteObject
    implements Echo
{
    private static final byte[] pattern = { (byte) 'A' };

    public EchoImpl(String protocol) throws RemoteException {
        super(0,
              new MultiSocketFactory.ClientFactory(protocol, pattern),
              new MultiSocketFactory.ServerFactory(protocol, pattern));
    }

    public byte[] echoNot(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++)
            result[i] = (byte) ~data[i];
        return result;
    }

    public static void main(String[] args) {
        /*
         * The following line is required with the JDK 1.2 VM so that the
         * VM can exit gracefully when this test completes.  Otherwise, the
         * conservative garbage collector will find a handle to the server
         * object on the native stack and not clear the weak reference to
         * it in the RMI runtime's object table.
         */
        Object dummy = new Object();

        try {
            String protocol = "";
            if (args.length >= 1)
                protocol = args[0];

            System.out.println("EchoServer: creating remote object");
            EchoImpl impl = new EchoImpl(protocol);
            int registryPort = Integer.parseInt(System.getProperty("rmi.registry.port"));
            System.out.println("EchoServer: binding in registry");
            Naming.rebind("//:" + registryPort +
                          "/EchoServer", impl);
            System.out.println("EchoServer ready.");
        } catch (Exception e) {
            System.err.println("EXCEPTION OCCURRED:");
            e.printStackTrace();
        }
    }
}
