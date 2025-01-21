/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.media.sound;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * Historically this class managed ensuring privileges to access resources
 * it is still used to get those resources but no longer does checks.
 *
 * @author Matthias Pfisterer
 */
final class JSSecurityManager {

    /** Prevent instantiation.
     */
    private JSSecurityManager() {
    }

    /**
     * Load properties from a file.
     * <p>
     * This method tries to load properties from the filename give into the
     * passed properties object. If the file cannot be found or something else
     * goes wrong, the method silently fails.
     * <p>
     * If the file referenced in "javax.sound.config.file" property exists and
     * the user has an access to it, then it will be loaded, otherwise default
     * configuration file "JAVA_HOME/conf/sound.properties" will be loaded.
     *
     * @param  properties the properties bundle to store the values of the
     *         properties file
     */
    static void loadProperties(final Properties properties) {
        final String customFile = System.getProperty("javax.sound.config.file");
        if (customFile != null) {
            if (loadPropertiesImpl(properties, customFile)) {
                return;
            }
        }
        final String home = System.getProperty("java.home");
        if (home == null) {
            throw new Error("Can't find java.home ??");
        }
        loadPropertiesImpl(properties, home, "conf", "sound.properties");
    }

    private static boolean loadPropertiesImpl(final Properties properties,
                                              String first, String... more) {
        final Path fname = Paths.get(first, more);
        try (final Reader reader = Files.newBufferedReader(fname)) {
            properties.load(reader);
            return true;
        } catch (final Throwable t) {
            return false;
        }
    }

    /** Create a Thread in the current ThreadGroup.
     */
    static Thread createThread(final Runnable runnable,
                               final String threadName,
                               final boolean isDaemon, final int priority,
                               final boolean doStart)
    {
        Thread thread = new Thread(null, runnable, threadName, 0, false);

        thread.setDaemon(isDaemon);
        if (priority >= 0) {
            thread.setPriority(priority);
        }
        if (doStart) {
            thread.start();
        }
        return thread;
    }

    static synchronized <T> List<T> getProviders(final Class<T> providerClass) {
        List<T> p = new ArrayList<>(7);
        final Iterator<T> ps = ServiceLoader.load(providerClass).iterator();

        while (ps.hasNext()) {
            try {
                // the iterator's next() method creates instances of the
                // providers and it should be called in the current security
                // context
                T provider = ps.next();
                if (providerClass.isInstance(provider)) {
                    // $$mp 2003-08-22
                    // Always adding at the beginning reverses the
                    // order of the providers. So we no longer have
                    // to do this in AudioSystem and MidiSystem.
                    p.add(0, provider);
                }
            } catch (Throwable t) {
                //$$fb 2002-11-07: do not fail on SPI not found
                if (Printer.err) t.printStackTrace();
            }
        }
        return p;
    }
}
