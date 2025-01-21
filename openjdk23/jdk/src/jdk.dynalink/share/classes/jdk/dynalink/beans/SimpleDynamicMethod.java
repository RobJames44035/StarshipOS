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
package jdk.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.dynalink.CallSiteDescriptor;

/**
 * A dynamic method bound to exactly one Java method or constructor that is not caller sensitive. Since its target is
 * not caller sensitive, this class pre-caches its method handle and always returns it from the call to
 * {@link #getTarget(CallSiteDescriptor)}. Can be used in general to represents dynamic methods bound to a single method handle,
 * even if that handle is not mapped to a Java method, i.e. as a wrapper around field getters/setters, array element
 * getters/setters, etc.
 */
class SimpleDynamicMethod extends SingleDynamicMethod {
    private final MethodHandle target;
    private final boolean constructor;

    /**
     * Creates a new simple dynamic method, with a name constructed from the class name, method name, and handle
     * signature.
     *
     * @param target the target method handle
     * @param clazz the class declaring the method
     * @param name the simple name of the method
     */
    SimpleDynamicMethod(final MethodHandle target, final Class<?> clazz, final String name) {
        this(target, clazz, name, false);
    }

    /**
     * Creates a new simple dynamic method, with a name constructed from the class name, method name, and handle
     * signature.
     *
     * @param target the target method handle
     * @param clazz the class declaring the method
     * @param name the simple name of the method
     * @param constructor does this represent a constructor?
     */
    SimpleDynamicMethod(final MethodHandle target, final Class<?> clazz, final String name, final boolean constructor) {
        super(getName(target, clazz, name, constructor));
        this.target = target;
        this.constructor = constructor;
    }

    private static String getName(final MethodHandle target, final Class<?> clazz, final String name, final boolean constructor) {
        return getMethodNameWithSignature(target.type(), constructor ? name : getClassAndMethodName(clazz, name), !constructor);
    }

    @Override
    boolean isVarArgs() {
        return target.isVarargsCollector();
    }

    @Override
    MethodType getMethodType() {
        return target.type();
    }

    @Override
    MethodHandle getTarget(final CallSiteDescriptor desc) {
        return target;
    }

    @Override
    boolean isConstructor() {
        return constructor;
    }
}
