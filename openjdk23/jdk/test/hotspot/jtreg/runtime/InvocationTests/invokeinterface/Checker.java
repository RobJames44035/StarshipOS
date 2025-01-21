/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package invokeinterface;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Checker extends shared.Checker {
    private Class interfaceClass;

    public Checker(Class interfaceClass, Class dynamicTargetClass) {
        super(interfaceClass, dynamicTargetClass);

        if (staticTargetClass.isInterface()) {
            this.interfaceClass = staticTargetClass;
        } else {
            throw new RuntimeException("Static target class should be an interface.");
        }
    }

    public String check (Class callerClass) {
        // Check access rights to interface for caller
        if (!checkAccess(interfaceClass, callerClass)) {
            return "java.lang.IllegalAccessError";
        }

        // NSME is thrown when interface doesn't declare the method
        if (getDeclaredMethod(interfaceClass) == null) {
            return "java.lang.NoSuchMethodError";
        }

        // 9.1.5 Access to Interface Member Names
        // "All interface members are implicitly public. They are
        // accessible outside the package where the interface is
        // declared if the interface is also declared public or
        // protected, in accordance with the rules of 6.6."

        // Search for method declaration in the hierarchy
        Class klass = dynamicTargetClass;

        while (klass != Object.class) {
            Method method = getDeclaredMethod(klass);

            if (method != null) {
                int modifiers = method.getModifiers();

                // Check whether obtained method is public and isn't abstract
                if ( Modifier.isPublic(modifiers)) {
                    if (Modifier.isAbstract(modifiers)) {
                        return "java.lang.AbstractMethodError";
                    } else {
                        return String.format("%s.%s",
                            method.getDeclaringClass().getSimpleName(),
                            methodName);
                    }
                } else {
                    // IAE is thrown when located method isn't PUBLIC
                    // or private.  Private methods are skipped when
                    // looking for an interface method.
                    if (!Modifier.isPrivate(modifiers)) {
                        return "java.lang.IllegalAccessError";
                    }
                }
            }

            klass = klass.getSuperclass();
        }

        // No method declaration is found
        return "java.lang.AbstractMethodError";
    }
}
