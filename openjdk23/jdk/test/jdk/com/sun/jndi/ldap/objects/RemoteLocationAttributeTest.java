/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.Hashtable;
import javax.naming.CommunicationException;
import javax.naming.NamingException;
import javax.naming.ServiceUnavailableException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import jdk.test.lib.net.URIBuilder;

/**
 * @test
 * @bug 8290367
 * @summary Check if com.sun.jndi.ldap.object.trustSerialData covers the creation
 *          of RMI remote objects from the 'javaRemoteLocation' LDAP attribute.
 * @modules java.naming/com.sun.jndi.ldap
 * @library /test/lib ../lib /javax/naming/module/src/test/test/
 * @build LDAPServer LDAPTestUtils
 *
 * @run main/othervm RemoteLocationAttributeTest
 * @run main/othervm -Dcom.sun.jndi.ldap.object.trustSerialData
 *                   RemoteLocationAttributeTest
 * @run main/othervm -Dcom.sun.jndi.ldap.object.trustSerialData=false
 *                   RemoteLocationAttributeTest
 * @run main/othervm -Dcom.sun.jndi.ldap.object.trustSerialData=true
 *                   RemoteLocationAttributeTest
 * @run main/othervm -Dcom.sun.jndi.ldap.object.trustSerialData=TrUe
 *                   RemoteLocationAttributeTest
 */

public class RemoteLocationAttributeTest {

    public static void main(String[] args) throws Exception {
        // Create unbound server socket
        ServerSocket serverSocket = new ServerSocket();

        // Bind it to the loopback address
        SocketAddress sockAddr = new InetSocketAddress(
                InetAddress.getLoopbackAddress(), 0);
        serverSocket.bind(sockAddr);

        // Construct the provider URL for LDAPTestUtils
        String providerURL = URIBuilder.newBuilder()
                .scheme("ldap")
                .loopback()
                .port(serverSocket.getLocalPort())
                .buildUnchecked().toString();

        Hashtable<Object, Object> env;

        // Initialize test environment variables
        env = LDAPTestUtils.initEnv(serverSocket, providerURL,
                RemoteLocationAttributeTest.class.getName(), args, false);

        DirContext ctx = null;
        try (serverSocket) {
            System.err.println(env);
            // connect to server
            ctx = new InitialDirContext(env);
            Object lookupResult = ctx.lookup("Test");
            System.err.println("Lookup result:" + lookupResult);
            // Test doesn't provide RMI registry running at 127.0.0.1:1097, but if
            // there is one running on test host successful result is valid for
            // cases when reconstruction allowed.
            if (!RECONSTRUCTION_ALLOWED) {
                throw new AssertionError("Unexpected successful lookup");
            }
        } catch (ServiceUnavailableException | CommunicationException connectionException) {
            // The remote location was properly reconstructed but connection to
            // RMI endpoint failed:
            //    ServiceUnavailableException - no open socket on 127.0.0.1:1097
            //    CommunicationException - 127.0.0.1:1097 is open, but it is not RMI registry
            System.err.println("Got one of connection exceptions:" + connectionException);
            if (!RECONSTRUCTION_ALLOWED) {
                throw new AssertionError("Reconstruction not blocked, as expected");
            }
        } catch (NamingException ne) {
            String message = ne.getMessage();
            System.err.printf("Got NamingException with message: '%s'%n", message);
            if (RECONSTRUCTION_ALLOWED && EXPECTED_NAMING_EXCEPTION_MESSAGE.equals(message)) {
                throw new AssertionError("Reconstruction unexpectedly blocked");
            }
            if (!RECONSTRUCTION_ALLOWED && !EXPECTED_NAMING_EXCEPTION_MESSAGE.equals(message)) {
                throw new AssertionError("Reconstruction not blocked");
            }
        } finally {
            LDAPTestUtils.cleanup(ctx);
        }
    }

    // Reconstruction of RMI remote objects is allowed if 'com.sun.jndi.ldap.object.trustSerialData'
    // is set to "true". If the system property is not specified it implies default "false" value
    private static final boolean RECONSTRUCTION_ALLOWED =
            Boolean.getBoolean("com.sun.jndi.ldap.object.trustSerialData");

    // NamingException message when reconstruction is not allowed
    private static final String EXPECTED_NAMING_EXCEPTION_MESSAGE = "Object deserialization is not allowed";
}
