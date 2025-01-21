/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * A JVM with JDP on should send multicast JDP packets regularly.
 *
 * See main for more information on running this test manually.
 * See Launcher classes for automated runs.
 *
 */

import java.net.SocketTimeoutException;
import java.util.Map;
import static jdk.test.lib.Asserts.assertNotEquals;

public class JdpOnTestCase extends JdpTestCase {

    private int receivedJDPpackets = 0;

    public JdpOnTestCase(ClientConnection connection) {
        super(connection);
    }

    /**
     * Subclasses: JdpOnTestCase and JdpOffTestCase have different messages.
     */
    @Override
    protected String initialLogMessage() {
        return "Waiting for 3 packets with jdp.name=" + connection.instanceName;
    }

    /**
     * This method is executed after a correct Jdp packet (coming from this VM) has been received.
     *
     * @param payload A dictionary containing the data if the received Jdp packet.
     */
    protected void packetFromThisVMReceived(Map<String, String> payload) {
        receivedJDPpackets++;
        final String jdpName = payload.get("INSTANCE_NAME");
        log.fine("Received correct JDP packet #" + String.valueOf(receivedJDPpackets) +
                ", jdp.name=" + jdpName);
        assertNotEquals(null, payload.get("PROCESS_ID"), "Expected payload.get(\"PROCESS_ID\") to be not null.");
    }

    /**
     * The socket should not timeout.
     * It is set to wait for a multiple of the defined pause between Jdp packets. See JdpTestCase.TIME_OUT_FACTOR.
     */
    @Override
    protected void onSocketTimeout(SocketTimeoutException e) throws Exception {
        String message = "Timed out waiting for JDP packet. Should arrive within " +
                connection.pauseInSeconds + " seconds, but waited for " +
                timeOut + " seconds.";
        log.severe(message);
        throw new Exception(message, e);
    }

    /**
     * After receiving three Jdp packets the test should end.
     */
    @Override
    protected boolean shouldContinue() {
        return receivedJDPpackets < 3;
    }

    /**
     * To run this test manually you might need the following VM options:
     * <p/>
     * -Dcom.sun.management.jmxremote.authenticate=false
     * -Dcom.sun.management.jmxremote.ssl=false
     * -Dcom.sun.management.jmxremote.port=4711    (or some other port number)
     * -Dcom.sun.management.jmxremote=true
     * -Dcom.sun.management.jmxremote.autodiscovery=true
     * -Dcom.sun.management.jdp.pause=1
     * -Dcom.sun.management.jdp.name=alex  (or some other string to identify this VM)
     * <p/>
     * Recommended for nice output:
     * -Djava.util.logging.SimpleFormatter.format="%1$tF %1$tT %4$-7s %5$s %n"
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JdpTestCase client = new JdpOnTestCase(new ClientConnection());
        client.run();
    }

}
