/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package compiler.tiered;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public class MethodHelper {
    /**
     * Gets method from a specified class using its name
     *
     * @param aClass type method belongs to
     * @param name   the name of the method
     * @return {@link Method} that represents corresponding class method
     */
    public static Method getMethod(Class<?> aClass, String name) {
        Method method;
        try {
            method = aClass.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            throw new Error("TESTBUG: Unable to get method " + name, e);
        }
        return method;
    }

    /**
     * Gets {@link Callable} that invokes given method from the given object
     *
     * @param object the object the specified method is invoked from
     * @param name   the name of the method
     */
    public static Callable<Integer> getCallable(Object object, String name) {
        Method method = getMethod(object.getClass(), name);
        return () -> {
            try {
                return Objects.hashCode(method.invoke(object));
            } catch (ReflectiveOperationException e) {
                throw new Error("TESTBUG: Invocation failure", e);
            }
        };
    }
}
