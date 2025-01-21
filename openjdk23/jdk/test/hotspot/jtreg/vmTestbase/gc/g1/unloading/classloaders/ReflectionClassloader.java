/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.classloaders;

/**
 * Classloader that will be useful for Class.forName classloading way.
 *
 */
public class ReflectionClassloader extends DoItYourselfClassLoader {

    private byte[] savedBytes;

    private String className;

    public ReflectionClassloader(byte[] savedBytes, String className) {
        this.savedBytes = savedBytes;
        this.className = className;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.equals(className)) {
            return defineClass(name, savedBytes);
        } else {
            return super.loadClass(name);
        }
    }

}
