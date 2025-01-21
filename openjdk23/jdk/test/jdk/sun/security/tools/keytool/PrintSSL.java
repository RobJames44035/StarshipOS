/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6480981 8160624
 * @summary keytool should be able to import certificates from remote SSL server
 * @library /test/lib
 * @build jdk.test.lib.SecurityTools
 *        jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 * @run main/othervm PrintSSL
 */

import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

public class PrintSSL {

    public static void main(String[] args) throws Throwable {
        Files.deleteIfExists(Paths.get("keystore"));

        // make sure that "-printcert" works with weak algorithms
        OutputAnalyzer out = SecurityTools.keytool("-genkeypair "
                + "-keystore keystore -storepass passphrase "
                + "-keypass passphrase -keyalg rsa -keysize 1024 "
                + "-sigalg MD5withRSA -alias rsa_alias -dname CN=Server");
        System.out.println(out.getOutput());
        out.shouldHaveExitValue(0);

        int port = new Server().start();
        if(port == -1) {
            throw new RuntimeException("Unable start ssl server.");
        }
        String vmOpt = System.getProperty("TESTTOOLVMOPTS");
        String cmd = String.format(
                "-debug %s -printcert -sslserver localhost:%s",
                ((vmOpt == null) ? "" : vmOpt ), port);

        out = SecurityTools.keytool(cmd);
        System.out.println(out.getOutput());
        out.shouldHaveExitValue(0);
    }

    private static class Server implements Runnable {

        private volatile int serverPort = -1;
        private final CountDownLatch untilServerReady = new CountDownLatch(1);

        public int start() throws InterruptedException {

            Thread server = new Thread(this);
            server.setDaemon(true);
            server.start();
            untilServerReady.await();
            return this.getServerPort();
        }

        @Override
        public void run() {

            System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
            System.setProperty("javax.net.ssl.keyStore", "keystore");
            SSLServerSocketFactory sslssf =
                (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            try (ServerSocket server = sslssf.createServerSocket(0)) {
                this.serverPort = server.getLocalPort();
                System.out.printf("%nServer started on: %s%n", getServerPort());
                untilServerReady.countDown();
                ((SSLSocket)server.accept()).startHandshake();
            } catch (Throwable e) {
                e.printStackTrace(System.out);
                untilServerReady.countDown();
            }

        }

        public int getServerPort() {
            return this.serverPort;
        }

    }
}

