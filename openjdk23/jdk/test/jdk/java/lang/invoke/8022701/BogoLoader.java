/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.ClassFile;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class BogoLoader extends ClassLoader {

    /**
     * Use this property to verify that the desired classloading is happening.
     */
    private final boolean verbose = Boolean.getBoolean("bogoloader.verbose");
    /**
     * Use this property to disable replacement for testing purposes.
     */
    private final boolean noReplace = Boolean.getBoolean("bogoloader.noreplace");

    /**
     * Set of class names that should be loaded with this loader.
     * Others are loaded with the system class loader, except for those
     * that are transformed.
     */
    private Set<String> nonSystem;

    /**
     * Map from class names to a bytecode transformer factory.
     */
    private Map<String, ClassTransform> replaced;

    /**
     * Keep track (not terribly efficiently) of which classes have already
     * been loaded by this class loader.
     */
    private final Vector<String> history = new Vector<String>();

    private boolean useSystemLoader(String name) {
        return ! nonSystem.contains(name) && ! replaced.containsKey(name);
    }

    public BogoLoader(Set<String> non_system, Map<String, ClassTransform> replaced) {
        super(Thread.currentThread().getContextClassLoader());
        this.nonSystem = non_system;
        this.replaced = replaced;
    }

    private byte[] readResource(String className) throws IOException {
        return readResource(className, "class");
    }

    private byte[] readResource(String className, String suffix) throws IOException {
        // Note to the unwary -- "/" works on Windows, leave it alone.
        String fileName = className.replace('.', '/') + "." + suffix;
        InputStream origStream = getResourceAsStream(fileName);
        if (origStream == null) {
            throw new IOException("Resource not found : " + fileName);
        }
        BufferedInputStream stream = new java.io.BufferedInputStream(origStream);
        byte[] data = new byte[stream.available()];
        int how_many = stream.read(data);
        // Really ought to deal with the corner cases of stream.available()
        return data;
    }

    protected byte[] getClass(String name) throws ClassNotFoundException,
    IOException {
        return readResource(name, "class");
    }

    /**
     * Loads the named class from the system class loader unless
     * the name appears in either replaced or nonSystem.
     * nonSystem classes are loaded into this classloader,
     * and replaced classes get their content from the specified array
     * of bytes (and are also loaded into this classloader).
     */
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class<?> clazz;

        if (history.contains(name)) {
            Class<?> c = this.findLoadedClass(name);
            return c;
        }
        if (useSystemLoader(name)) {
            clazz = findSystemClass(name);
            if (verbose) System.err.println("Loading system class " + name);
        } else {
            history.add(name);
            try {
                if (verbose) {
                    System.err.println("Loading classloader class " + name);
                }
                byte[] classData = getClass(name);
                boolean expanded = false;
                if (!noReplace && replaced.containsKey(name)) {
                    if (verbose) {
                        System.err.println("Replacing class " + name);
                    }
                    var cf = ClassFile.of();
                    classData = cf.transformClass(cf.parse(classData), replaced.get(name));
                }
                clazz = defineClass(name, classData, 0, classData.length);
            } catch (java.io.EOFException ioe) {
                throw new ClassNotFoundException(
                        "IO Exception in reading class : " + name + " ", ioe);
            } catch (ClassFormatError ioe) {
                throw new ClassNotFoundException(
                        "ClassFormatError in reading class file: ", ioe);
            } catch (IOException ioe) {
                throw new ClassNotFoundException(
                        "IO Exception in reading class file: ", ioe);
            }
        }
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        if (resolve) {
            resolveClass(clazz);
        }
        return clazz;
    }
}
