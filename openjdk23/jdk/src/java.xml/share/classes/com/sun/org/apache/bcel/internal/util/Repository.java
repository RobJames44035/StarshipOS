/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.util;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;

/**
 * Abstract definition of a class repository. Instances may be used to load classes from different sources and may be
 * used in the Repository.setRepository method.
 *
 * @see org.apache.bcel.Repository
 * @LastModified: Feb 2023
 */
public interface Repository {

    /**
     * Clears all entries from cache.
     */
    void clear();

    /**
     * Finds the class with the name provided, if the class isn't there, return NULL.
     */
    JavaClass findClass(String className);

    /**
     * Finds the JavaClass instance for the given run-time class object.
     *
     * @throws ClassNotFoundException if the class can't be found.
     */
    JavaClass loadClass(Class<?> clazz) throws ClassNotFoundException;

    /**
     * Finds the class with the name provided, if the class isn't there, make an attempt to load it.
     *
     * @throws ClassNotFoundException if the class can't be found.
     */
    JavaClass loadClass(String className) throws ClassNotFoundException;

    /**
     * Removes class from repository
     */
    void removeClass(JavaClass clazz);

    /**
     * Stores the provided class under "clazz.getClassName()"
     */
    void storeClass(JavaClass clazz);
}
