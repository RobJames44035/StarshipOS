/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8140450 8268829
 * @summary Sanity test for exception cases
 * @run junit SanityTest
 */

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;
import static java.lang.StackWalker.Option.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

public class SanityTest {
    @Test
    public void testNPE() {
        assertThrows(NullPointerException.class, () ->
                StackWalker.getInstance((Set<StackWalker.Option>) null));
        assertThrows(NullPointerException.class, () ->
                StackWalker.getInstance((StackWalker.Option) null));
    }

    private static Stream<StackWalker> noRetainClassRef() {
        return Stream.of(StackWalker.getInstance(), StackWalker.getInstance(DROP_METHOD_INFO));
    }

    @ParameterizedTest
    @MethodSource("noRetainClassRef")
    public void testUOE(StackWalker sw) {
        assertThrows(UnsupportedOperationException.class, () -> sw.getCallerClass());
    }

    @Test
    public void testInvalidEstimateDepth() {
        assertThrows(IllegalArgumentException.class, () ->
                StackWalker.getInstance(Collections.emptySet(), 0));
    }

    @Test
    public void testNullFunction() {
        assertThrows(NullPointerException.class, () ->
                StackWalker.getInstance().walk(null));
    }

    @Test
    public void testNullConsumer() {
        assertThrows(NullPointerException.class, () ->
                StackWalker.getInstance().forEach(null));
    }

    @ParameterizedTest
    @MethodSource("noRetainClassRef")
    public void testUOEFromGetDeclaringClass(StackWalker sw) {
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::getDeclaringClass));
    }

    @ParameterizedTest
    @MethodSource("noRetainClassRef")
    public void testUOEFromGetMethodType(StackWalker sw) {
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::getMethodType));
    }

    private static Stream<StackWalker> noMethodInfo() {
        return Stream.of(StackWalker.getInstance(DROP_METHOD_INFO),
                         StackWalker.getInstance(Set.of(DROP_METHOD_INFO, RETAIN_CLASS_REFERENCE)));
    }

    @ParameterizedTest
    @MethodSource("noMethodInfo")
    public void testNoMethodInfo(StackWalker sw) {
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::getMethodName));
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::getMethodType));
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::getDescriptor));
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::getByteCodeIndex));
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::getFileName));
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::isNativeMethod));
        assertThrows(UnsupportedOperationException.class, () ->
                sw.forEach(StackWalker.StackFrame::toStackTraceElement));
    }
}
