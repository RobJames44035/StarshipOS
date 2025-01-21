/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.share;

/**
 * This loader's <tt>findClass()</tt> method is dummy.
 */
public class DummyClassLoader extends ClassLoader {
    /**
     * Cannot instantiate w/o a parent loader.
     */
    protected DummyClassLoader() {
    }

    /**
     * Delegate everything to the <tt>parent</tt> loader.
     */
    public DummyClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Do nothing: parent loader must load everything.
     *
     * @throws ClassNotFoundException In any case.
     */
    public Class findClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException(name);
    }
}
