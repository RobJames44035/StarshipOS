/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared;

import vm.runtime.defmeth.shared.data.method.body.CallMethod;
import vm.runtime.defmeth.shared.executor.MHInvokeWithArgsTest;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Encapsulates test context and provides utility methods to invoke methods
 * in that context through reflection. Should be loaded in the same class loader as all other test classes.
 * It is needed for correct resolution of initiating class loader in case of reflection invocation scenario.
 */
public class TestContext {
    public static Object invoke(Method m, Object obj, Object... args)
            throws InvocationTargetException, IllegalAccessException
    {
        return m.invoke(obj, args);
    }

    public static Object invoke(Constructor m, Object... args)
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
        return m.newInstance(args);
    }

    public static Object invokeWithArguments(CallMethod.Invoke invokeType, Class<?> declaringClass,
                                             String methodName, MethodType type, Object... arguments) throws Throwable {
        // Need to do method lookup in the right context
        // Otherwise, ClassNotFoundException is thrown
        MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        MethodHandle mh;

        // FYI: can't use switch over enum here, because to implement it javac produces auxiliary class TestContext$1
        // and it can't be accessed from the context where TestContext is loaded
        if (invokeType == CallMethod.Invoke.VIRTUAL || invokeType == CallMethod.Invoke.INTERFACE) {
            mh = LOOKUP.findVirtual(declaringClass, methodName, type);
        } else if (invokeType == CallMethod.Invoke.SPECIAL) {
            if (methodName.equals("<init>") && type.returnType() == void.class) {
                mh = LOOKUP.findConstructor(declaringClass, type);
            } else {
                mh = LOOKUP.findSpecial(declaringClass, methodName, type, declaringClass);
            }
        } else if (invokeType == CallMethod.Invoke.STATIC) {
            mh = LOOKUP.findStatic(declaringClass, methodName, type);
        } else {
            throw new Error("Unknown invoke instruction: "+invokeType);
        }

        return mh.invokeWithArguments(arguments);
    }
}

