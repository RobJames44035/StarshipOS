/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package jdk.vm.ci.runtime.test;

import jdk.vm.ci.meta.ResolvedJavaMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Context for method related tests.
 */
public class MethodUniverse extends TypeUniverse {

    public static final Map<Method, ResolvedJavaMethod> methods = new HashMap<>();
    public static final Map<Constructor<?>, ResolvedJavaMethod> constructors = new HashMap<>();

    {
        for (Class<?> c : classes) {
            for (Method m : c.getDeclaredMethods()) {
                ResolvedJavaMethod method = metaAccess.lookupJavaMethod(m);
                methods.put(m, method);
            }
            for (Constructor<?> m : c.getDeclaredConstructors()) {
                constructors.put(m, metaAccess.lookupJavaMethod(m));
            }
        }
    }
}
