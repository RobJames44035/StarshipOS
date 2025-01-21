/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.System.Logger;
import java.lang.System.LoggerFinder;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class BaseLoggerFinder extends LoggerFinder
        implements BasePlatformLoggerTest.TestLoggerFinder {

    public static final RuntimePermission LOGGERFINDER_PERMISSION =
            new RuntimePermission("loggerFinder");

    @Override
    public Logger getLogger(String name, Module caller) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(LOGGERFINDER_PERMISSION);
        }
        PrivilegedAction<ClassLoader> pa = () -> caller.getClassLoader();
        ClassLoader callerLoader = AccessController.doPrivileged(pa);
        if (callerLoader == null) {
            return system.computeIfAbsent(name, (n) -> new LoggerImpl(n));
        } else {
            return user.computeIfAbsent(name, (n) -> new LoggerImpl(n));
        }
    }
}

