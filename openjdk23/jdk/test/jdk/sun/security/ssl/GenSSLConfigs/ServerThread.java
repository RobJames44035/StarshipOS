/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

import java.io.*;
import javax.net.ssl.*;

class ServerThread extends TestThread
    implements HandshakeCompletedListener
{
    // Basic server identity info
    private int                 port;
    private SSLServerSocketFactory factory;

    private int                 backlog = 50;
    private SSLServerSocket     ss;

    // Crypto options ... start with basic ciphers, renegotiation
    // can use a different set.
    private String              renegotiateSuites [];

    // Flags controlling testing -- passed to handler
    private boolean             needClientAuth;

    // Flags controlling testing -- main server thread only
    private boolean             useMT;

    ServerThread (int port, int backlog, SSLContext ctx)
    {
        super ("Server");
        setDaemon (true);

        this.port = port;
        this.backlog = backlog;
        factory = ctx.getServerSocketFactory();
    }


    // NEGOTIATION OPTIONS

    public void setRenegotiateSuites (String suites [])
        { renegotiateSuites = suites; }


    // DEFINE WHAT IS BEING TESTED

    public void setNeedClientAuth (boolean flag)
        { needClientAuth = flag; }
    public boolean getNeedClientAuth ()
        { return needClientAuth; }

    public void setUseMT (boolean flag)
        { useMT = flag; }
    public boolean getUseMT ()
        { return useMT; }

    public int getServerPort() {
        return port;
    }


    public void waitTillReady ()
    {
        synchronized (this) {
            while (ss == null)
                try { wait (); }
                catch (InterruptedException e) { }
        }
    }


    public void run ()
    {
        // NOT TESTING:
        //      - connection backlog
        //      - binding to IP address
        //      - base class methods

        try {
            synchronized (this) {
                ss = (SSLServerSocket)
                    factory.createServerSocket(port, backlog);
                port = ss.getLocalPort();
                notify ();
            }

            if (needClientAuth)
                ss.setNeedClientAuth (true);
            if (basicCipherSuites != null)
                ss.setEnabledCipherSuites (basicCipherSuites);

            out.println ("%% Starting " + getName ());

        } catch (Throwable t) {
            synchronized (out) {
                out.println ("%% Error in " + getName ());
                t.printStackTrace (out);
            }

            return;
        }

            // XXX for N connections ...

        while (true) {
            try {
                SSLSocket       s = (SSLSocket) ss.accept ();

                if (listenHandshake)
                    s.addHandshakeCompletedListener (this);

                if (verbosity >= 1)
                    out.println ("%% " + getName () + " accepted from "
                        + s.getInetAddress ().getHostName ()
                        + ":" + s.getPort ());

                ServerHandler   handler = new ServerHandler (s);

                handler.setVerbosity (verbosity);
                handler.setOutput (out);

                handler.setDoRenegotiate (doRenegotiate);
                handler.setInitiateHandshake (initiateHandshake);
                handler.setListenHandshake (listenHandshake);
                handler.setReverseRole (reverseRole);
                handler.setNeedClientAuth (needClientAuth);

                if (prng != null)
                    handler.setPRNG (prng);

                if (useMT)
                    handler.start ();
                else
                    handler.run ();

            } catch (Throwable t) {
                synchronized (out) {
                    out.println ("%% Error in " + getName ());
                    t.printStackTrace (out);
                }


                return;
            }
        }
    }


    public void handshakeCompleted (HandshakeCompletedEvent event)
    {
        if (verbosity >= 2) {
            out.println ("%% Handshook: " + event.getSource ());

            // if more verbosity, give cert chain
        }
    }
}
