/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package java.util;

import static java.lang.StackWalker.Option.*;
import java.lang.StackWalker.StackFrame;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

import jdk.internal.reflect.CallerSensitive;
import jdk.internal.reflect.Reflection;

public class CSM {
    private static StackWalker walker =
        StackWalker.getInstance(EnumSet.of(RETAIN_CLASS_REFERENCE,
                                           DROP_METHOD_INFO,
                                           SHOW_HIDDEN_FRAMES,
                                           SHOW_REFLECT_FRAMES));

    public static class Result {
        public final List<Class<?>> callers;
        public final List<StackWalker.StackFrame> frames;
        Result(List<Class<?>> callers,
               List<StackFrame> frames) {
            this.callers = callers;
            this.frames = frames;
        }
    }

    /**
     * Returns the caller of this caller-sensitive method returned by
     * by Reflection::getCallerClass.
     *
     * StackWalker::getCallerClass is expected to throw UOE
     */
    @CallerSensitive
    public static Class<?> caller() {
        Class<?> c1 = Reflection.getCallerClass();

        try {
            Class<?> c2 = walker.getCallerClass();
            throw new RuntimeException("Exception not thrown by StackWalker::getCallerClass. Returned " + c2.getName());
        } catch (UnsupportedOperationException e) {}
        return c1;
    }

    /**
     * Returns the caller of this non-caller-sensitive method returned
     * by StackWalker::getCallerClass
     */
    public static Result getCallerClass() {
        Class<?> caller = walker.getCallerClass();
        return new Result(List.of(caller), dump());
    }

    private static final Method GET_CALLER_CLASS;
    static {
        Method m = null;
        try {
            m = StackWalker.class.getMethod("getCallerClass");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        GET_CALLER_CLASS = m;
    }

    /**
     * Returns the caller of this non-caller-sensitive method returned
     * by StackWalker::getCallerClass invoked via core reflection
     */
    public static Result getCallerClassReflectively() throws ReflectiveOperationException {
        Class<?> caller = (Class<?>)GET_CALLER_CLASS.invoke(walker);
        return new Result(List.of(caller), dump());
    }

    static List<StackFrame> dump() {
        return walker.walk(s -> s.collect(Collectors.toList()));
    }
}
