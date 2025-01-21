/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share.pool;

import jdk.test.lib.util.Pair;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Represents a holder that contains test methods
 */
public abstract class MethodHolder {
    /**
     * Helper method to get executable for the specified method
     *
     * @param holder class that holds specified method
     * @param name method name
     * @param parameterTypes parameter types of the specified method
     * @return {@link Method} instance
     */
    public Method getMethod(MethodHolder holder,
                             String name,
                             Class<?>... parameterTypes) {
        try {
            return holder.getClass().getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new Error("TESTBUG: Can't get method " + name, e);
        }
    }

    /**
     * Gets all test methods
     *
     * @return pairs of Executable and appropriate Callable
     */
    public List<Pair<Executable, Callable<?>>> getAllMethods() {
        Class<?> aClass = this.getClass();
        Object classInstance;
        try {
            classInstance = aClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new Error("TESTBUG: unable to get new instance", e);
        }
        List<Pair<Executable, Callable<?>>> pairs = new ArrayList<>();
        {
            Method method = getMethod(this, "method", int.class, String[].class,
                    Integer.class, byte[].class, double[][].class);
            Pair<Executable, Callable<?>> pair = new Pair<>(method,
                    () -> {
                        // Make args
                        int a = 0;
                        String[] ss = {"a", "b", "c", "d"};
                        Integer i = 1;
                        byte[] bb = {1, 2};
                        double[][] dd = {
                                {1.618033, 3.141592},
                                {2.718281, 0.007874}
                        };
                        // Invoke method
                        method.invoke(classInstance, a, ss, i, bb, dd);
                        return true;
                    });
            pairs.add(pair);
        }
        {
            Method method = getMethod(this, "method");
            Pair<Executable, Callable<?>> pair = new Pair<>(method,
                    () -> {
                        method.invoke(classInstance);
                        return true;
                    });
            pairs.add(pair);
        }
        {
            Method method = getMethod(this, "smethod");
            Pair<Executable, Callable<?>> pair = new Pair<>(method,
                    () -> method.invoke(classInstance));
            pairs.add(pair);
        }
        {
            Method method = getMethod(this, "smethod", int.class, int[].class);
            Pair<Executable, Callable<?>> pair = new Pair<>(method,
                    () -> {
                        int[] array = {1, 2, 3};
                        return method.invoke(classInstance, 42, array);
                    });
            pairs.add(pair);
        }
        {
            Method method = getMethod(this, "smethod", Integer.class);
            Pair<Executable, Callable<?>> pair = new Pair<>(method,
                    () -> method.invoke(classInstance, 100));
            pairs.add(pair);
        }
        return pairs;
    }
}
