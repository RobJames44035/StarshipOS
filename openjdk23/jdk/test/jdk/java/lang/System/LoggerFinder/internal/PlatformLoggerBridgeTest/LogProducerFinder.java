/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.lang.System.LoggerFinder;
import java.lang.System.Logger;

public class LogProducerFinder extends LoggerFinder {

    static final RuntimePermission LOGGERFINDER_PERMISSION =
            new RuntimePermission("loggerFinder");
    final ConcurrentHashMap<String, PlatformLoggerBridgeTest.LoggerImpl>
            system = new ConcurrentHashMap<>();
    final ConcurrentHashMap<String, PlatformLoggerBridgeTest.LoggerImpl>
            user = new ConcurrentHashMap<>();

    private static PlatformLoggerBridgeTest.LoggerImpl newLoggerImpl(String name) {
        return new PlatformLoggerBridgeTest.LoggerImpl(name);
    }

    @Override
    public Logger getLogger(String name, Module caller) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(LOGGERFINDER_PERMISSION);
        }
        PrivilegedAction<ClassLoader> pa = () -> caller.getClassLoader();
        ClassLoader callerLoader = AccessController.doPrivileged(pa);
        if (callerLoader == null) {
            return system.computeIfAbsent(name, (n) -> newLoggerImpl(n));
        } else {
            return user.computeIfAbsent(name, (n) -> newLoggerImpl(n));
        }
    }
}
