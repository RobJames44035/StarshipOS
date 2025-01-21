/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package boot;

import static java.lang.System.out;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetCallerClass {

    public Class<?> missingCallerSensitiveAnnotation() {
        return jdk.internal.reflect.Reflection.getCallerClass();
    }

    @jdk.internal.reflect.CallerSensitive
    public Class<?> getCallerClass() {
        var caller = jdk.internal.reflect.Reflection.getCallerClass();
        out.println("caller: " + caller);
        out.println(StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk(toStackTrace()));
        return caller;
    }

    private Class<?> getCallerClass(Class<?> caller) {
        out.println("caller: " + caller);
        out.println(StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk(toStackTrace()));
        return caller;
    }

    @jdk.internal.reflect.CallerSensitive
    public static Class<?> getCallerClassStatic() {
        var caller = jdk.internal.reflect.Reflection.getCallerClass();
        out.println("caller: " + caller);
        out.println(StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk(toStackTrace()));
        return caller;
    }

    private static Class<?> getCallerClassStatic(Class<?> caller) {
        out.println("caller: " + caller);
        out.println(StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk(toStackTrace()));
        return caller;
    }

    @jdk.internal.reflect.CallerSensitive
    public Class<?> getCallerClassNoAlt() {
        var caller = jdk.internal.reflect.Reflection.getCallerClass();
        out.println("caller: " + caller);
        out.println(StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk(toStackTrace()));
        return caller;
    }

    @jdk.internal.reflect.CallerSensitive
    public static Class<?> getCallerClassStaticNoAlt() {
        var caller = jdk.internal.reflect.Reflection.getCallerClass();
        out.println("caller: " + caller);
        out.println(StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk(toStackTrace()));
        return caller;
    }

    private static Function<Stream<StackWalker.StackFrame>, String> toStackTrace() {
        return frames -> frames
            .takeWhile(
                frame -> !frame.getClassName().equals("GetCallerClassTest") ||
                         !frame.getMethodName().equals("main"))
            .map(Object::toString)
            .collect(Collectors.joining("\n  ", "  ", "\n"));
    }
}

