/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
import java.util.logging.Logger;

/*
 * This class is loaded onto the call stack when the getLogger methods are
 * called and then the classes classloader can be used to find a bundle in
 * the same directory as the class.  However, Logger is not allowed
 * to find the bundle by looking up the stack for this classloader.
 * We verify that this cannot happen.
 *
 * @author Jim Gish
 */
public class LoadItUp1 {
    public Logger getAnonymousLogger(String rbName) throws Exception {
        // we should not be able to find the resource in this directory via
        // getLogger calls.  The only way that would be possible given this setup
        // is that if Logger.getLogger searched up the call stack
        return Logger.getAnonymousLogger(rbName);
    }

    public Logger getLogger(String loggerName) {
        return Logger.getLogger(loggerName);
    }

    public Logger getLogger(String loggerName,String bundleName) {
        return Logger.getLogger(loggerName, bundleName);
    }
}
