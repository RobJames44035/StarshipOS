/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

public class JSSEServer extends CipherTestUtils.Server {

    private final SSLServerSocket serverSocket;
    private static volatile boolean closeServer = false;

    JSSEServer(CipherTestUtils cipherTest, int serverPort,
            String protocol, String cipherSuite) throws Exception {
        super(cipherTest);
        SSLContext serverContext = SSLContext.getInstance("TLS");
        serverContext.init(new KeyManager[]{cipherTest.getServerKeyManager()},
                new TrustManager[]{cipherTest.getServerTrustManager()},
                CipherTestUtils.secureRandom);
        SSLServerSocketFactory factory =
                (SSLServerSocketFactory)serverContext.getServerSocketFactory();
        serverSocket =
                (SSLServerSocket) factory.createServerSocket(serverPort);
        serverSocket.setEnabledProtocols(protocol.split(","));
        serverSocket.setEnabledCipherSuites(cipherSuite.split(","));

        CipherTestUtils.printInfo(serverSocket);
    }

    @Override
    public void run() {
        System.out.println("JSSE Server listening on port " + getPort());
        while (!closeServer) {
            try (final SSLSocket socket = (SSLSocket) serverSocket.accept()) {
                socket.setSoTimeout(CipherTestUtils.TIMEOUT);

                try (InputStream in = socket.getInputStream();
                        OutputStream out = socket.getOutputStream()) {
                    handleRequest(in, out);
                    out.flush();
                } catch (IOException e) {
                    CipherTestUtils.addFailure(e);
                    System.out.println("Got IOException:");
                    e.printStackTrace(System.out);
                }
            } catch (Exception e) {
                CipherTestUtils.addFailure(e);
                System.out.println("Exception:");
                e.printStackTrace(System.out);
            }
        }
    }

    int getPort() {
        return serverSocket.getLocalPort();
    }

    @Override
    public void close() throws IOException {
        closeServer = true;
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }
}
