/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BooleanSupplier;

import jdk.internal.logger.BootstrapLogger;

public final class BootstrapLoggerUtils {

    private static final Field IS_BOOTED;
    private static final Method AWAIT_PENDING;

    static {
        try {
            IS_BOOTED = BootstrapLogger.class.getDeclaredField("isBooted");
            IS_BOOTED.setAccessible(true);
            // private reflection hook that allows us to test wait until all
            // the tasks pending in the BootstrapExecutor are finished.
            AWAIT_PENDING = BootstrapLogger.class.getDeclaredMethod("awaitPendingTasks");
            AWAIT_PENDING.setAccessible(true);
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void setBootedHook(BooleanSupplier supplier) throws IllegalAccessException {
        IS_BOOTED.set(null, supplier);
    }

    public static void awaitPending() {
        try {
            AWAIT_PENDING.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
}
