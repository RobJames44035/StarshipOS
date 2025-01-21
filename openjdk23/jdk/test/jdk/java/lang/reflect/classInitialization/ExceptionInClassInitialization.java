/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @build Initializer Test ExceptionInClassInitialization
 * @run main ExceptionInClassInitialization
 * @summary ensure InvocationTargetException thrown due to the initialization of
 *          the declaring class wrapping with the proper cause
 */

import java.lang.reflect.InvocationTargetException;

public class ExceptionInClassInitialization {
    public static void main(String... argv) throws ReflectiveOperationException {
        Class<?> c = Class.forName("Initializer");
        testExecMethod(c);
        testFieldAccess(c);
    }

    static void testExecMethod(Class<?> cls) throws ReflectiveOperationException {
        try {
            cls.getDeclaredMethod("execMethod").invoke(null);
            throw new RuntimeException("InvocationTargetException not thrown");
        } catch (InvocationTargetException e) {
            // InvocationTargetException wraps the exception that was thrown while reflection.
            Throwable t = e.getCause();
            if (t instanceof ExceptionInInitializerError eiie) {
                if (eiie.getCause() instanceof MyException) {
                    return;
                }
                throw new RuntimeException("ExceptionInInitializerError due to other exception than MyException!", eiie);
            }
            throw new RuntimeException("InvocationTargetException was thrown not due to error while initialization!", e);
        }
    }

    static void testFieldAccess(Class<?> cls) throws ReflectiveOperationException {
        try {
            cls.getDeclaredMethod("fieldAccess").invoke(null);
            throw new RuntimeException("InvocationTargetException not thrown");
        } catch (InvocationTargetException e) {
            // the class initialization was run and failed.  NoClassDefFoundError
            // should be thrown in this second attempt.
            Throwable t = e.getCause();
            if (t instanceof NoClassDefFoundError ncdfe) {
                t = t.getCause();
                if (t instanceof ExceptionInInitializerError eiie) {
                    return;
                }
            }
            throw new RuntimeException("InvocationTargetException was thrown not due to error while initialization!", e);
        }
    }

}
