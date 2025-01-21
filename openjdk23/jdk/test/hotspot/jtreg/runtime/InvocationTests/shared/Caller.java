/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package shared;

import java.lang.reflect.InvocationTargetException;

/*******************************************************************/
// Invoke different target method callers
/*******************************************************************/

public class Caller {
    private ClassLoader loader;
    private Class paramClass;
    private Class targetClass;
    private boolean passed = true;
    private Checker checker;

    public Caller(ClassLoader loader, Checker checker,
                  Class paramClass, Class targetClass) {
        this.loader = loader;
        this.paramClass = paramClass;
        this.targetClass = targetClass;
        this.checker = checker;
    }

    public boolean isPassed() {
        return passed;
    }

    public String call(String invoker) {
        try {
            Class clazz = loader.loadClass(invoker);

            String expectedBehavior = checker.check(clazz);

            String result = null;
            Throwable exc = null;
            try {
                java.lang.reflect.Method m = clazz.getDeclaredMethod("call", paramClass);
                result = (String) m.invoke(null, targetClass.newInstance());
            } catch (InvocationTargetException e) {
                exc = e.getCause();
            } catch (Throwable e) {
                exc = e;
            }

            if (result == null) {
                if (exc != null) {
                    result = exc.getClass().getName();
                } else {
                    result = "null";
                }
            }

            if (!(result.equals(expectedBehavior) || "".equals(expectedBehavior)) ) {
                passed = false;
                result = String.format("%s/%s", result, expectedBehavior);
            }

            return Checker.abbreviateResult(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
