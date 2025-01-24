package org.starship

import groovy.util.logging.Slf4j

import java.nio.file.Files
import java.nio.file.Path

/**
 * BundleManager is the main class responsible for managing the server-side logic. 
 * It listens for client connections, handles communication via a Unix Domain Socket (UDS),
 * and sends periodic heartbeats to connected clients.
 *
 * Features:
 * - Accepts a socket path and heartbeat interval as arguments.
 * - Starts a server that manages client connections.
 * - Sends heartbeats to maintain communication with clients.
 *
 * Constants:
 * - DEFAULT_HEARTBEAT_INTERVAL_MS: Default interval for heartbeats in milliseconds.
 * - DEFAULT_SOCKET_PATH: Default path to the Unix Domain Socket.
 * - UTF_8: Character encoding used for communication.
 */
@Slf4j

class BundleManager {

    // Constants
    private static final long DEFAULT_HEARTBEAT_INTERVAL_MS = 1000
    private static final String DEFAULT_SOCKET_PATH = "/tmp/bundlemanager.sock"
    public static final String UTF_8 = "UTF-8"

    
    /**
    * Main entry point for the **BundleManager** application.
    *
    * This method initializes the server by parsing command-line arguments for
    * the Unix Domain Socket (UDS) path and heartbeat interval. It sets up the
    * server to listen for client connections and handles the initialization of
    * the heartbeats and server operations.
    *
    * @param args Command-line arguments:
    *             <ul>
    *               <li>args[0] - Optional: Path to the Unix Domain Socket. Defaults to {@link #DEFAULT_SOCKET_PATH}.</li>
    *               <li>args[1] - Optional: Heartbeat interval in milliseconds. Defaults to {@link #DEFAULT_HEARTBEAT_INTERVAL_MS}.</li>
    *             </ul>
    */
    static void main(String[] args) {
        log.info("Starting BundleManager process...")

        // Parse arguments for socket path and heartbeat interval
        String socketPath = args.length > 0 ? args[0] : DEFAULT_SOCKET_PATH
        long heartbeatIntervalMs = args.length > 1 ? args[1].toLong() : DEFAULT_HEARTBEAT_INTERVAL_MS

        // Start IPC Server and Heartbeat
        //noinspection GroovyUnusedAssignment
        BundleManager bundleManager = new BundleManager()
        runServer(socketPath, heartbeatIntervalMs)

        while(true) {
            try {

// TODO Main logic to manage bundles
                Thread.sleep(1000) // Supervisor polling interval
            } catch (Exception e) {
                log.error("Error in supervisor loop: ${e.message}", e)
            }
        }
    }
    
    /**
    * Starts the server to handle client connections and sends heartbeats to connected clients.
    *
    * This method ensures that a clean socket path is used, initializes the server socket,
    * listens for new client connections in an infinite loop, and creates a new thread
    * for each connected client to handle heartbeats.
    *
    * @param socketPath The Unix Domain Socket (UDS) path used by the server to communicate with clients.
    * @param heartbeatIntervalMs The interval, in milliseconds, at which heartbeats are sent to the clients.
    */
    static void runServer(String socketPath, long heartbeatIntervalMs) {
        // Ensure the socket path is clean (delete previous file if present)
        Path udsPath = Path.of(socketPath)
        try {
            Files.deleteIfExists(udsPath)
        } catch (Exception e) {
            log.error("Failed to clean previous UDS path: ${e.message}")
        }

        ServerSocket serverSocket = new ServerSocket()
        serverSocket.bind(new InetSocketAddress(socketPath as int))

        log.info("BundleManager started. Listening on: $socketPath")

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept()
                log.debug("Client connected.")

                // Start a thread to handle heartbeats for the client
                Thread.start {
                    sendHeartbeats(clientSocket, heartbeatIntervalMs)
                }
            } catch (Exception e) {
                log.error("Error in server: ${e.message}")
            }
        }
    }

    /**
    * Sends periodic heartbeats to the connected client.
    *
    * This method runs continuously in a loop, sending heartbeat messages
    * at the specified interval to maintain a connection with the client.
    * If an error occurs or the client disconnects, the method terminates
    * and ensures the client socket is closed properly.
    *
    * @param clientSocket The client socket to which heartbeats are sent.
    * @param intervalMs The interval, in milliseconds, between each heartbeat.
    */
    private static void sendHeartbeats(Socket clientSocket, long intervalMs) {
        try (OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream(), UTF_8)) {
            while (!Thread.currentThread().isInterrupted() && clientSocket.isConnected()) {
                writer.write("heartbeat\n")
                writer.flush()
                Thread.sleep(intervalMs)
            }
        } catch (Exception e) {
            log.error("Error while sending heartbeats: ${e.message}")
        } finally {
            try {
                clientSocket.close()
            } catch (Exception e) {
                log.error("Failed to close client socket: ${e.message}")
            }
        }
    }
}
