/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package com.sun.jmx.remote.util;

public class OrderClassLoaders extends ClassLoader {
    public OrderClassLoaders(ClassLoader cl1, ClassLoader cl2) {
        super(cl1);

        this.cl2 = cl2;
    }

    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException cne) {
            if (cl2 != null) {
                return cl2.loadClass(name);
            } else {
                throw cne;
            }
        }
    }

    private ClassLoader cl2;
}
