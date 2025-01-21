/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.internal;

import java.lang.invoke.MethodHandles;
import jdk.internal.module.Modules;
import jdk.jfr.Event;

public final class SecuritySupport {
    private static final Module JFR_MODULE = Event.class.getModule();
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    static {
        // ensure module java.base can read module jdk.jfr as early as possible
        addReadEdge(Object.class);
        addInternalEventExport(Object.class);
        addEventsExport(Object.class);
    }

    static void makeVisibleToJFR(Class<?> clazz) {
        Module classModule = clazz.getModule();
        Modules.addReads(JFR_MODULE, classModule);
        if (clazz.getPackage() != null) {
            String packageName = clazz.getPackage().getName();
            Modules.addExports(classModule, packageName, JFR_MODULE);
            Modules.addOpens(classModule, packageName, JFR_MODULE);
        }
    }

    /**
     * Adds a qualified export of the internal.jdk.jfr.internal.event package
     * (for EventConfiguration and EventWriter)
     */
    static void addInternalEventExport(Class<?> clazz) {
        Modules.addExports(JFR_MODULE, "jdk.jfr.internal.event", clazz.getModule());
    }

    static void addEventsExport(Class<?> clazz) {
        Modules.addExports(JFR_MODULE, "jdk.jfr.events", clazz.getModule());
    }

    static void addReadEdge(Class<?> clazz) {
        Modules.addReads(clazz.getModule(), JFR_MODULE);
    }

    public static void ensureClassIsInitialized(Class<?> clazz) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, LOOKUP);
            lookup.ensureInitialized(clazz);
        } catch (IllegalAccessException e) {
            throw new InternalError(e);
        }
    }

    static Class<?> defineClass(Class<?> lookupClass, byte[] bytes) {
        try {
            return MethodHandles.privateLookupIn(lookupClass, LOOKUP).defineClass(bytes);
        } catch (IllegalAccessException e) {
            throw new InternalError(e);
        }
    }
}
