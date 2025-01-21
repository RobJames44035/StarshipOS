/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.share.jpda;

import nsk.share.*;
import nsk.share.jdi.Binder;

/**
 * This class implements communicational channel between
 * debugger and debugee used for synchronization and data exchange.
 * This channel is based on TCP/IP sockets and works in all
 * modes (local, remote and manual). In a remote mode
 * connection to <code>BindServer</code> is used for redirecting IOPipe messages.
 * In all other modes direct TCP/IP coonnection between two VMs is used.
 *
 * @see BindServer
 */
public class IOPipe extends SocketIOPipe {

    public static final byte PORTS_COUNT = 10;
    public static final byte NO_PORTS = 0;

    public static final String PIPE_LOG_PREFIX = "IOPipe> ";

    /**
      * Make <code>IOPipe</code> at debugee's side.
      *
      * @deprecated Use DebugeeArgumentHandler.createDebugeeIOPipe(Log) instead.
      *
      * @see DebugeeArgumentHandler#createDebugeeIOPipe(Log)
      */
    @Deprecated
    public IOPipe(DebugeeArgumentHandler argumentHandler, Log log) {
        this(log, getTestHost(argumentHandler), argumentHandler.getPipePortNumber(),
                (long)argumentHandler.getWaitTime() * 60 * 1000, false);
    }

    /**
      * Make <code>IOPipe</code> at debugger's side
      * with given <code>Debugee</code> mirror.
      *
      * @deprecated Preferred way is to start IOPipe before launching debuggee process.
      *
      * @see #startDebuggerPipe
      */
    @Deprecated
    public IOPipe(DebugeeProcess debugee) {
        this(debugee.getLog(),
                debugee.getArgumentHandler().getDebugeeHost(),
                debugee.getArgumentHandler().getPipePortNumber(),
                (long)debugee.getArgumentHandler().getWaitTime() * 60 * 1000,
                true);
        setServerSocket(debugee.getPipeServerSocket());
        if (debugee.pipe != null) {
            throw new RuntimeException("debugee pipe is already set");
        }
        debugee.pipe = this;
    }

    /**
      * Make general <code>IOPipe</code> object with specified parameters.
      */
    private IOPipe(Log log, String host, int port, long timeout, boolean listening) {
        super("IOPipe", log, PIPE_LOG_PREFIX, host, port, timeout, listening);
    }

    /**
     * Creates and starts listening <code>IOPipe</code> at debugger side.
     */
    public static IOPipe startDebuggerPipe(DebugeeBinder binder) {
        IOPipe ioPipe = new IOPipe(binder.getLog(),
                binder.getArgumentHandler().getDebugeeHost(),
                binder.getArgumentHandler().getPipePortNumber(),
                (long)binder.getArgumentHandler().getWaitTime() * 60 * 1000,
                true);
        ioPipe.setServerSocket(binder.getPipeServerSocket());
        ioPipe.startListening();
        return ioPipe;
    }

    protected void connect() {
        super.connect();
    }

    /**
     * Get appropriate test host name relying on the provided argumnets.
     */
    private static String getTestHost(DebugeeArgumentHandler argumentHandler) {
        return argumentHandler.getTestHost();
    }

} // IOPipe
