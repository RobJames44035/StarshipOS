/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.internal.access;

import jdk.internal.foreign.abi.NativeEntryPoint;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface JavaLangInvokeAccess {
    /**
     * Returns the declaring class for the given ResolvedMethodName.
     * Used by {@code StackFrameInfo}.
     */
    Class<?> getDeclaringClass(Object rmname);

    /**
     * Returns the {@code MethodType} for the given method descriptor
     * and class loader.
     * Used by {@code StackFrameInfo}.
     */
    MethodType getMethodType(String descriptor, ClassLoader loader);

    /**
     * Returns true if the given flags has MN_CALLER_SENSITIVE flag set.
     */
    boolean isCallerSensitive(int flags);

    /**
     * Returns true if the given flags has MN_HIDDEN_MEMBER flag set.
     */
    boolean isHiddenMember(int flags);

    /**
     * Returns a map of class name in internal forms to its corresponding
     * class bytes per the given stream of LF_RESOLVE and SPECIES_RESOLVE
     * trace logs. Used by GenerateJLIClassesPlugin to enable generation
     * of such classes during the jlink phase.
     */
    Map<String, byte[]> generateHolderClasses(Stream<String> traces);

    /**
     * Returns a var handle view of a given memory segment.
     * Used by {@code jdk.internal.foreign.LayoutPath} and
     * {@code java.lang.invoke.MethodHandles}.
     */
    VarHandle memorySegmentViewHandle(Class<?> carrier, long alignmentMask, ByteOrder order);

    /**
     * Var handle carrier combinator.
     * Used by {@code java.lang.invoke.MethodHandles}.
     */
    VarHandle filterValue(VarHandle target, MethodHandle filterToTarget, MethodHandle filterFromTarget);

    /**
     * Var handle filter coordinates combinator.
     * Used by {@code java.lang.invoke.MethodHandles}.
     */
    VarHandle filterCoordinates(VarHandle target, int pos, MethodHandle... filters);

    /**
     * Var handle drop coordinates combinator.
     * Used by {@code java.lang.invoke.MethodHandles}.
     */
    VarHandle dropCoordinates(VarHandle target, int pos, Class<?>... valueTypes);

    /**
     * Var handle permute coordinates combinator.
     * Used by {@code java.lang.invoke.MethodHandles}.
     */
    VarHandle permuteCoordinates(VarHandle target, List<Class<?>> newCoordinates, int... reorder);

    /**
     * Var handle collect coordinates combinator.
     * Used by {@code java.lang.invoke.MethodHandles}.
     */
    VarHandle collectCoordinates(VarHandle target, int pos, MethodHandle filter);

    /**
     * Var handle insert coordinates combinator.
     * Used by {@code java.lang.invoke.MethodHandles}.
     */
    VarHandle insertCoordinates(VarHandle target, int pos, Object... values);

    /**
     * Returns a native method handle with given arguments as fallback and steering info.
     *
     * Will allow JIT to intrinsify.
     *
     * @param nep the native entry point
     * @return the native method handle
     */
    MethodHandle nativeMethodHandle(NativeEntryPoint nep);

    /**
     * Produces a method handle unreflecting from a {@code Constructor} with
     * the trusted lookup
     */
    MethodHandle unreflectConstructor(Constructor<?> ctor) throws IllegalAccessException;

    /**
     * Produces a method handle unreflecting from a {@code Field} with
     * the trusted lookup
     */
    MethodHandle unreflectField(Field field, boolean isSetter) throws IllegalAccessException;

    /**
     * Produces a method handle of a virtual method with the trusted lookup.
     */
    MethodHandle findVirtual(Class<?> defc, String name, MethodType type) throws IllegalAccessException;

    /**
     * Produces a method handle of a static method with the trusted lookup.
     */
    MethodHandle findStatic(Class<?> defc, String name, MethodType type) throws IllegalAccessException;

    /**
     * Returns a method handle of an invoker class injected for core reflection
     * implementation with the following signature:
     *     reflect_invoke_V(MethodHandle mh, Object target, Object[] args)
     *
     * The invoker class is a hidden class which has the same
     * defining class loader, runtime package, and protection domain
     * as the given caller class.
     */
    MethodHandle reflectiveInvoker(Class<?> caller);

    /**
     * A best-effort method that tries to find any exceptions thrown by the given method handle.
     * @param handle the handle to check
     * @return an array of exceptions, or {@code null}.
     */
    Class<?>[] exceptionTypes(MethodHandle handle);

    /**
     * Returns a method handle that allocates an instance of the given class
     * and then invoke the given constructor of one of its superclasses.
     *
     * This method should only be used by ReflectionFactory::newConstructorForSerialization.
     */
    MethodHandle serializableConstructor(Class<?> decl, Constructor<?> ctorToCall) throws IllegalAccessException;
}
