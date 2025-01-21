/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.AllPermission;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * A custom ClassLoader to load the concrete LoggerFinder class
 * with all permissions.
 *
 * @author danielfuchs
 */
public class CustomSystemClassLoader extends ClassLoader {


    final ConcurrentHashMap<String, Class<?>> classes = new ConcurrentHashMap<>();

    public CustomSystemClassLoader() {
        super();
    }
    public CustomSystemClassLoader(ClassLoader parent) {
        super(parent);
    }

    private Class<?> defineFinderClass(String name)
        throws ClassNotFoundException {
        Class<?> loggerFinderClass = classes.get(name);
        if (loggerFinderClass != null) return loggerFinderClass;
        final Object obj = getClassLoadingLock(name);
        synchronized(obj) {
            loggerFinderClass = classes.get(name);
            if (loggerFinderClass != null) return loggerFinderClass;

            URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
            File file = new File(url.getPath(), name+".class");
            if (file.canRead()) {
                try {
                    byte[] b = Files.readAllBytes(file.toPath());
                    Permissions perms = new Permissions();
                    perms.add(new AllPermission());
                    loggerFinderClass = defineClass(
                            name, b, 0, b.length, new ProtectionDomain(
                            this.getClass().getProtectionDomain().getCodeSource(),
                            perms));
                    System.out.println("Loaded " + name);
                    classes.put(name, loggerFinderClass);
                    return loggerFinderClass;
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    throw new ClassNotFoundException(name, ex);
                }
            } else {
                throw new ClassNotFoundException(name,
                        new IOException(file.toPath() + ": can't read"));
            }
        }
    }

    @Override
    public synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.equals("LogProducerFinder") || name.startsWith("LogProducerFinder$")) {
            Class<?> c = defineFinderClass(name);
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.equals("LogProducerFinder") || name.startsWith("LogProducerFinder$")) {
            return defineFinderClass(name);
        }
        return super.findClass(name);
    }

}
