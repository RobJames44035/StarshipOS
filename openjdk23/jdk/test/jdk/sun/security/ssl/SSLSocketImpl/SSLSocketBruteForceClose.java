/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

//
// Please run in othervm mode.  SunJSSE does not support dynamic system
// properties, no way to re-use system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 8209333
 * @summary Socket reset issue for TLS 1.3 socket close
 * @library /javax/net/ssl/templates
 * @run main/othervm SSLSocketBruteForceClose
 */

import javax.net.ssl.*;
import java.io.*;
import java.net.SocketException;

public class SSLSocketBruteForceClose extends SSLSocketTemplate {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i<= 10; i++) {
            System.err.println("===================================");
            System.err.println("loop " + i);
            System.err.println("===================================");
            new SSLSocketBruteForceClose().run();
        }
    }

    @Override
    protected void configureServerSocket(SSLServerSocket socket) {
        socket.setNeedClientAuth(false);
        socket.setEnableSessionCreation(true);
        socket.setUseClientMode(false);
    }

    @Override
    protected void runServerApplication(SSLSocket socket) throws Exception {
        System.err.println("Reading data from client");
        BufferedReader serverReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        String data = serverReader.readLine();
        System.err.println("Received data from client: " + data);

        System.err.println("Reading more data from client");
        data = serverReader.readLine();
        System.err.println("Received data from client: " + data);
    }

    @Override
    protected void configureClientSocket(SSLSocket socket) {
        try {
            socket.setSoLinger(true, 3);
            socket.setSoTimeout(1000);
        } catch (SocketException exc) {
            throw new RuntimeException("Could not configure client socket", exc);
        }
    }

    @Override
    protected void runClientApplication(SSLSocket socket) throws Exception {
        String clientData = "Hi, I am client";

        System.err.println("Sending data to server ...");
        BufferedWriter os = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        os.write(clientData, 0, clientData.length());
        os.newLine();
        os.flush();

        System.err.println("Sending more data to server ...");
        os.write(clientData, 0, clientData.length());
        os.newLine();
        os.flush();

        socket.close();
        System.err.println("client socket closed");
    }
}

