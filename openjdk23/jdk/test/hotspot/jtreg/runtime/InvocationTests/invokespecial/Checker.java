/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package invokespecial;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Checker extends shared.Checker {

    public Checker(Class staticTargetClass, Class dynamicTargetClass) {
        super(staticTargetClass, dynamicTargetClass);
    }

    public String check (Class callerClass) {
        // If objectref is null, the invokespecial instruction throws a NullPointerException.
        if (dynamicTargetClass == null) {
            return "java.lang.NullPointerException";
        }

        // TODO: find a citation from spec for this case
        Method resolvedMethod;
        try {
            // May throw VerifyError
            resolvedMethod = getMethodInHierarchy(staticTargetClass);
        } catch (Throwable e) {
            return e.getClass().getName();
        }

        if (resolvedMethod == null) {
            return "java.lang.NoSuchMethodError";
        }

       // If:
       //   - the resolved method is protected (4.7)
       //   - it is a member of a superclass of the current class
       //   - the method is not declared in the same run-time package (5.3) as the current class
       // then:
       //   the class of objectref must be either the current class or a subclass of the
       // current class.

        if (Modifier.isProtected(resolvedMethod.getModifiers())) {
            Method methodInSuperclass = getMethodInHierarchy(resolvedMethod.getDeclaringClass().getSuperclass());

            if (methodInSuperclass != null) {
                String resolvedMethodPkg = getClassPackageName(resolvedMethod.getDeclaringClass());
                String methodInSuperclassPkg = getClassPackageName(methodInSuperclass.getDeclaringClass());

                if (!resolvedMethodPkg.equals(methodInSuperclassPkg)) {
                    //TODO: clarify this
//                    if (callerClass == methodInSuperclass.getDeclaringClass()) {
//                        return "java.lang.IllegalAccessError";
//                    }
                }
            }
        }

       /*
        * The resolved method is selected for invocation unless all of
        * the following conditions are true:
        *     * TODO: The ACC_SUPER flag (see Table 4.1, "Class access and property
        *       modifiers") is set for the current class.
        *     * The class of the resolved method is a superclass of the
        *       current class - assumed by construction procedure
        *
        *     * The resolved method is not an instance initialization method (3.9).
        */
        if (!"<init>".equals(methodName)) {
           /*
            * Let C be the direct superclass of the current class:
            *    * If C contains a declaration for an instance method with the same
            *      name and descriptor as the resolved method, then this method will be
            *      invoked. The lookup procedure terminates.
            *    * Otherwise, if C has a superclass, this same lookup procedure is
            *      performed recursively using the direct superclass of C. The method to
            *      be invoked is the result of the recursive invocation of this lookup
            *      procedure.
            *    * Otherwise, an AbstractMethodError is raised.
            *      TODO: so far, sometimes NSME is thrown
            */
            Class klass = dynamicTargetClass.getSuperclass();

            while (klass != Object.class) {
                Method method = getDeclaredMethod(klass);

                if (method != null) {
                    /*
                     * If the resolved method is a class (static) method, the
                     * invokespecial instruction throws an IncompatibleClassChangeError.
                     */
                    if (Modifier.isStatic(method.getModifiers())) {
                        return "java.lang.IncompatibleClassChangeError";
                    }

                    // Check access rights
                    if ( checkAccess(method, callerClass)
//                         && !(
//                                 Modifier.isProtected(method.getModifiers())
//                                 && (
//                                     staticTargetClass.isAssignableFrom(callerClass)
//                                     || getClassPackageName(staticTargetClass).equals(getClassPackageName(callerClass))
//                                    )
//
//                            )
                        )
                    {
                        return String.format("%s.%s"
                                , method.getDeclaringClass().getSimpleName()
                                , methodName
                                );
                    } else {
                        // IAE is thrown when located method can't be accessed from the call site
                        return "java.lang.IllegalAccessError";
                    }
                }

                klass = klass.getSuperclass();
            }

            return "java.lang.AbstractMethodError";
        } else {
            // The resolved method is an instance initialization method (3.9).
        }

        // TODO: change
        return "---";
    }
}
