/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package evalexpr;

import java.util.Map;

/**
 * A class loader which loads classes from byte arrays.
 *
 * <p><b>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 * @author Peter von der Ah&eacute;
 */
public class ByteArrayClassLoader extends ClassLoader {
    /**
     * Maps binary class names to class files stored as byte arrays.
     */
    private Map<String, byte[]> classes;

    /**
     * Creates a new instance of ByteArrayClassLoader
     * @param classes a map from binary class names to class files stored as byte arrays
     */
    public ByteArrayClassLoader(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException e) {
            byte[] classData = classes.get(name);
            return defineClass(name, classData, 0, classData.length);
        }
    }
}
