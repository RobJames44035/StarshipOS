/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package jdk.internal.reflect;

import java.lang.reflect.InvocationTargetException;

abstract class ConstructorAccessorImpl implements ConstructorAccessor {
    /** Matches specification in {@link java.lang.reflect.Constructor} */
    public abstract Object newInstance(Object[] args)
        throws InstantiationException,
               IllegalArgumentException,
               InvocationTargetException;
}
