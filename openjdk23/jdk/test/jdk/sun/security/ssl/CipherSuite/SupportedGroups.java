/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

 /*
  * @test
  * @bug 8171279
  * @library /javax/net/ssl/templates
  * @summary Test TLS connection with each individual supported group
  * @run main/othervm SupportedGroups x25519
  * @run main/othervm SupportedGroups x448
  * @run main/othervm SupportedGroups secp256r1
  * @run main/othervm SupportedGroups secp384r1
  * @run main/othervm SupportedGroups secp521r1
  * @run main/othervm SupportedGroups ffdhe2048
  * @run main/othervm SupportedGroups ffdhe3072
  * @run main/othervm SupportedGroups ffdhe4096
  * @run main/othervm SupportedGroups ffdhe6144
  * @run main/othervm SupportedGroups ffdhe8192
 */
import java.net.InetAddress;
import java.util.Arrays;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;

public class SupportedGroups extends SSLSocketTemplate {

    private static volatile int index;
    private static final String[][][] protocols = {
        {{"TLSv1.3"}, {"TLSv1.3"}},
        {{"TLSv1.3", "TLSv1.2"}, {"TLSv1.2"}},
        {{"TLSv1.2"}, {"TLSv1.3", "TLSv1.2"}},
        {{"TLSv1.2"}, {"TLSv1.2"}}
    };

    public SupportedGroups() {
        this.serverAddress = InetAddress.getLoopbackAddress();
    }

    // Servers are configured before clients, increment test case after.
    @Override
    protected void configureClientSocket(SSLSocket socket) {
        String[] ps = protocols[index][0];

        System.out.print("Setting client protocol(s): ");
        Arrays.stream(ps).forEachOrdered(System.out::print);
        System.out.println();

        socket.setEnabledProtocols(ps);
    }

    @Override
    protected void configureServerSocket(SSLServerSocket serverSocket) {
        String[] ps = protocols[index][1];

        System.out.print("Setting server protocol(s): ");
        Arrays.stream(ps).forEachOrdered(System.out::print);
        System.out.println();

        serverSocket.setEnabledProtocols(ps);
    }

    /*
     * Run the test case.
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("jdk.tls.namedGroups", args[0]);

        for (index = 0; index < protocols.length; index++) {
            (new SupportedGroups()).run();
        }
    }
}
