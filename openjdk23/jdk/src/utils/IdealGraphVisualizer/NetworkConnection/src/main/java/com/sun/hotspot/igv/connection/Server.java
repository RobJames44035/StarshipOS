/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package com.sun.hotspot.igv.connection;

import com.sun.hotspot.igv.data.GraphDocument;
import com.sun.hotspot.igv.data.serialization.Parser;
import com.sun.hotspot.igv.data.serialization.Printer.GraphContextAction;
import com.sun.hotspot.igv.settings.Settings;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 *
 * @author Thomas Wuerthinger
 */
public class Server implements PreferenceChangeListener {
    private ServerSocketChannel serverSocket;
    private final GraphDocument graphDocument;
    private final GraphContextAction contextAction;
    private int port;

    private volatile boolean isServerRunning;

    public Server(GraphDocument graphDocument, GraphContextAction contextAction) {
        this.graphDocument = graphDocument;
        this.contextAction = contextAction;
        port = Integer.parseInt(Settings.get().get(Settings.PORT, Settings.PORT_DEFAULT));
        Settings.get().addPreferenceChangeListener(this);
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent e) {
        int curPort = Integer.parseInt(Settings.get().get(Settings.PORT, Settings.PORT_DEFAULT));
        if (curPort != port) {
            port = curPort;
            shutdownServer();
            startServer();
        }
    }

    public void startServer() {
        isServerRunning = true;

        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(port));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        Runnable client = () -> {
            while (isServerRunning) {
                try {
                    SocketChannel clientSocket = serverSocket.accept();
                    if (!isServerRunning) {
                        clientSocket.close();
                        return;
                    }
                    new Thread(() -> {
                        try (clientSocket) {
                            clientSocket.configureBlocking(true);
                            clientSocket.socket().getOutputStream().write('y');
                            new Parser(clientSocket, null, graphDocument, contextAction).parse();
                        } catch (IOException ignored) {}
                    }).start();
                } catch (IOException ex) {
                    if (isServerRunning) {
                        ex.printStackTrace();
                    }
                    return;
                }
            }
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };

        new Thread(client).start();
    }

    public void shutdownServer() {
        isServerRunning = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
