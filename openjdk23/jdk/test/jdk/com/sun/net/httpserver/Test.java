/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import com.sun.net.httpserver.*;
import java.util.logging.*;

public class Test {

    static Logger logger;

    static void enableLogging() {
        logger = Logger.getLogger("com.sun.net.httpserver");
        Handler h = new ConsoleHandler();
        h.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(h);
    }
}
