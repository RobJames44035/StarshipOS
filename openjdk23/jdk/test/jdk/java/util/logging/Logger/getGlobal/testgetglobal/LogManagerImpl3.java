/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package testgetglobal;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class is used to verify that calling Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
 * in the static initializer of a LogManager subclass installed as default
 * LogManager does not cause issues beyond throwing the expected NPE.
 * @author danielfuchs
 */
public class LogManagerImpl3 extends LogManager {

    static final Logger global;
    static {
        Logger g = null;
        try {
            g = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            throw new Error("Should not have reached here");
        } catch (Exception x) {
            // This is to be expected: Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
            // will call LogManager.getLogManager() which will return null, since
            // we haven't manage to do new LogManagerImpl3() yet.
            //
            System.err.println("Got expected exception - you cannot call"
                   + " Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)"
                   + " in LogManager subclass static initializer: " + x);
            x.printStackTrace();
        }
        if (g == null) {
            g = Logger.getGlobal();
        }
        global = g;
        System.err.println("Global is: " + global);
    }

}
