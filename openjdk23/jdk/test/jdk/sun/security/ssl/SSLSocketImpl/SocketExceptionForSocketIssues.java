/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

//
// Please run in othervm mode.  SunJSSE does not support dynamic system
// properties, no way to re-use system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 8214339
 * @summary SSLSocketImpl erroneously wraps SocketException
 * @library /javax/net/ssl/templates
 * @run main/othervm SocketExceptionForSocketIssues
 */

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SocketExceptionForSocketIssues extends SSLSocketTemplate {

    private final CountDownLatch waitForClient = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        System.out.println("===================================");
        new SocketExceptionForSocketIssues().run();
    }

    @Override
    protected void configureServerSocket(SSLServerSocket socket) {
        socket.setNeedClientAuth(false);
        socket.setEnableSessionCreation(true);
        socket.setUseClientMode(false);
    }

    @Override
    protected void runServerApplication(SSLSocket socket) throws Exception {
        try {
            if (!waitForClient.await(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("Client didn't complete within 5 seconds.");
            }

            System.out.println("Sending data to client ...");
            String serverData = "Hi, I am server";
            BufferedWriter os = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            os.write(serverData, 0, serverData.length());
            os.newLine();
            os.flush();
            throw new RuntimeException("The expected SocketException was not thrown.");
        } catch (SocketException se) {
            // the expected exception, ignore it
            System.out.println("Caught expected SocketException: " + se);
        }
    }

    @Override
    protected void configureClientSocket(SSLSocket socket) {
        try {
            socket.setSoLinger(true, 3);
            socket.setSoTimeout(100);
        } catch (SocketException exc) {
            throw new RuntimeException("Could not configure client socket.", exc);
        }
    }

    @Override
    protected void runClientApplication(SSLSocket socket) throws Exception {
        try {
            String clientData = "Hi, I am client";
            BufferedWriter os = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            os.write(clientData, 0, clientData.length());
            os.newLine();
            os.flush();

            System.out.println("Reading data from server");
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String data = is.readLine();
            System.out.println("Received Data from server: " + data);

            throw new RuntimeException("The expected client exception was not thrown.");

        } catch (SSLProtocolException | SSLHandshakeException sslhe) {
            System.err.println("Client had unexpected SSL exception: " + sslhe);
            throw sslhe;

        } catch (SSLException | SocketTimeoutException ssle) {
            // the expected exception, ignore it
            System.out.println("Caught expected client exception: " + ssle);

        } finally {
            waitForClient.countDown();
        }
    }
}
