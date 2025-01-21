/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package sun.reflect.misc;

import jdk.internal.reflect.Reflection;

public final class ReflectUtil {

    private ReflectUtil() {
    }

    /**
     * Ensures that access to a method or field is granted and throws
     * IllegalAccessException if not. This method is not suitable for checking
     * access to constructors.
     *
     * @param currentClass the class performing the access
     * @param memberClass the declaring class of the member being accessed
     * @param target the target object if accessing instance field or method;
     *               or null if accessing static field or method or if target
     *               object access rights will be checked later
     * @param modifiers the member's access modifiers
     * @throws IllegalAccessException if access to member is denied
     * @implNote Delegates directly to
     *           {@link Reflection#ensureMemberAccess(Class, Class, Class, int)}
     *           which should be used instead.
     */
    public static void ensureMemberAccess(Class<?> currentClass,
                                          Class<?> memberClass,
                                          Object target,
                                          int modifiers)
        throws IllegalAccessException
    {
        Reflection.ensureMemberAccess(currentClass,
                                      memberClass,
                                      target == null ? null : target.getClass(),
                                      modifiers);
    }
}
