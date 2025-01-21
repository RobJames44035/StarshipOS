/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The AppleImpl class implements the behavior of the remote "apple"
 * objects exported by the application.
 */
public class AppleImpl extends UnicastRemoteObject implements Apple {

    private static final Logger logger = Logger.getLogger("reliability.apple");
    private final String name;

    public AppleImpl(String name) throws RemoteException {
        this.name = name;
    }

    /**
     * Receive an array of AppleEvent objects.
     */
    public void notify(AppleEvent[] events) {
        String threadName = Thread.currentThread().getName();
        logger.log(Level.FINEST,
            threadName + ": " + toString() + ".notify: BEGIN");

        for (int i = 0; i < events.length; i++) {
            logger.log(Level.FINEST,
                threadName + ": " + toString() + ".notify(): events["
                + i + "] = " + events[i].toString());
        }

        logger.log(Level.FINEST,
            threadName + ": " + toString() + ".notify(): END");
    }

    /**
     * Return a newly created and exported orange implementation.
     */
    public Orange newOrange(String name) throws RemoteException {
        String threadName = Thread.currentThread().getName();
        logger.log(Level.FINEST,
            threadName + ": " + toString() + ".newOrange(" + name + "): BEGIN");

        Orange orange = new OrangeImpl(name);

        logger.log(Level.FINEST,
            threadName + ": " + toString() + ".newOrange(" + name + "): END");

        return orange;
    }

    public String toString() {
        return name;
    }
}
