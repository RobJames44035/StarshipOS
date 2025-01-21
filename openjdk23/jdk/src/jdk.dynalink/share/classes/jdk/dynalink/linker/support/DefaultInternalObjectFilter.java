/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file, and Oracle licenses the original version of this file under the BSD
 * license:
 */
package jdk.dynalink.linker.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.dynalink.DynamicLinkerFactory;
import jdk.dynalink.linker.MethodHandleTransformer;

/**
 * Default implementation for a
 * {@link DynamicLinkerFactory#setInternalObjectsFilter(MethodHandleTransformer)}
 * that delegates to a pair of filtering method handles. It takes a method
 * handle of {@code Object(Object)} type for filtering parameter values and
 * another one of the same type for filtering return values. It applies them as
 * parameter and return value filters on method handles passed to its
 * {@link #transform(MethodHandle)} method, on those parameters and return values
 * that are declared to have type {@link Object}. Also handles
 * {@link MethodHandle#isVarargsCollector() method handles that support variable
 * arity calls} with a last {@code Object[]} parameter. You can broadly think of
 * the parameter filter as being a wrapping method for exposing internal runtime
 * objects wrapped into an adapter with some public interface, and the return
 * value filter as being its inverse unwrapping method.
 * @since 9
 */
public class DefaultInternalObjectFilter implements MethodHandleTransformer {
    private static final MethodHandle FILTER_VARARGS = new Lookup(MethodHandles.lookup()).findStatic(
            DefaultInternalObjectFilter.class, "filterVarArgs", MethodType.methodType(Object[].class, MethodHandle.class, Object[].class));

    private final MethodHandle parameterFilter;
    private final MethodHandle returnFilter;
    private final MethodHandle varArgFilter;

    /**
     * Creates a new filter.
     * @param parameterFilter the filter for method parameters. Must be of type
     * {@code Object(Object)}, or {@code null}.
     * @param returnFilter the filter for return values. Must be of type
     * {@code Object(Object)}, or {@code null}.
     * @throws IllegalArgumentException if one or both filters are not of the
     * expected type.
     */
    public DefaultInternalObjectFilter(final MethodHandle parameterFilter, final MethodHandle returnFilter) {
        this.parameterFilter = checkHandle(parameterFilter, "parameterFilter");
        this.returnFilter = checkHandle(returnFilter, "returnFilter");
        this.varArgFilter = parameterFilter == null ? null : FILTER_VARARGS.bindTo(parameterFilter);
    }

    @Override
    public MethodHandle transform(final MethodHandle target) {
        assert target != null;
        MethodHandle[] filters = null;
        final MethodType type = target.type();
        final boolean isVarArg = target.isVarargsCollector();
        final int paramCount = type.parameterCount();
        final MethodHandle paramsFiltered;
        // Filter parameters
        if (parameterFilter != null) {
            int firstFilter = -1;
            // Ignore receiver, start from argument 1
            for(int i = 1; i < paramCount; ++i) {
                final Class<?> paramType = type.parameterType(i);
                final boolean filterVarArg = isVarArg && i == paramCount - 1 && paramType == Object[].class;
                if (filterVarArg || paramType == Object.class) {
                    if (filters == null) {
                        firstFilter = i;
                        filters = new MethodHandle[paramCount - firstFilter];
                    }
                    filters[i - firstFilter] = filterVarArg ? varArgFilter : parameterFilter;
                }
            }
            paramsFiltered = filters != null ? MethodHandles.filterArguments(target, firstFilter, filters) : target;
        } else {
            paramsFiltered = target;
        }
        // Filter return value if needed
        final MethodHandle returnFiltered = returnFilter != null && type.returnType() == Object.class ? MethodHandles.filterReturnValue(paramsFiltered, returnFilter) : paramsFiltered;
        // Preserve varargs collector state
        return isVarArg && !returnFiltered.isVarargsCollector() ? returnFiltered.asVarargsCollector(type.parameterType(paramCount - 1)) : returnFiltered;

    }

    private static MethodHandle checkHandle(final MethodHandle handle, final String handleKind) {
        if (handle != null) {
            final MethodType objectObjectType = MethodType.methodType(Object.class, Object.class);
            if (!handle.type().equals(objectObjectType)) {
                throw new IllegalArgumentException("Method type for " + handleKind + " must be " + objectObjectType);
            }
        }
        return handle;
    }

    @SuppressWarnings("unused")
    private static Object[] filterVarArgs(final MethodHandle parameterFilter, final Object[] args) throws Throwable {
        Object[] newArgs = null;
        for(int i = 0; i < args.length; ++i) {
            final Object arg = args[i];
            final Object newArg = parameterFilter.invokeExact(arg);
            if (arg != newArg) {
                if (newArgs == null) {
                    newArgs = args.clone();
                }
                newArgs[i] = newArg;
            }
        }
        return newArgs == null ? args : newArgs;
    }
}
