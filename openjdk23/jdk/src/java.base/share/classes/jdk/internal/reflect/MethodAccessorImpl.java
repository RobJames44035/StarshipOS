/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package jdk.internal.reflect;

import java.lang.reflect.InvocationTargetException;

/** <P> This class is known to the VM; do not change its name without
    also changing the VM's code. </P>

    <P> NOTE: ALL methods of subclasses are skipped during security
    walks up the stack. The assumption is that the only such methods
    that will persistently show up on the stack are the implementing
    methods for java.lang.reflect.Method.invoke(). </P>
*/

abstract class MethodAccessorImpl implements MethodAccessor {
    /** Matches specification in {@link java.lang.reflect.Method} */
    public abstract Object invoke(Object obj, Object[] args)
        throws IllegalArgumentException, InvocationTargetException;

    public Object invoke(Object obj, Object[] args, Class<?> caller)
            throws IllegalArgumentException, InvocationTargetException {
        return invoke(obj, args);
    }
}
