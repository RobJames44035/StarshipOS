/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/* @test
 * @bug 6198277
 * @summary Test that each ListeningConnector that supports a "timeout" argument will
 * timeout with TransportTimeoutException
 */
import com.sun.jdi.Bootstrap;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.ListeningConnector;
import com.sun.jdi.connect.TransportTimeoutException;
import java.util.List;
import java.util.Map;

public class AcceptTimeout {

    public static void main(String args[]) throws Exception {
        List<ListeningConnector> connectors = Bootstrap.virtualMachineManager().listeningConnectors();
        for (ListeningConnector lc: connectors) {
            Map<String,Connector.Argument> cargs = lc.defaultArguments();
            Connector.IntegerArgument timeout = (Connector.IntegerArgument)cargs.get("timeout");

            /*
             * If the Connector has a argument named "timeout" then we set the timeout to 1 second
             * and start it listening on its default address. It should throw TranpsortTimeoutException.
             */
            if (timeout != null) {
                System.out.println("Testing " + lc.name());
                timeout.setValue(1000);

                System.out.println("Listening on: " + lc.startListening(cargs));
                try {
                    lc.accept(cargs);
                    throw new RuntimeException("Connection accepted from some debuggee - unexpected!");
                } catch (TransportTimeoutException e) {
                    System.out.println("Timed out as expected.\n");
                }
                lc.stopListening(cargs);
            }
        }
    }
}
