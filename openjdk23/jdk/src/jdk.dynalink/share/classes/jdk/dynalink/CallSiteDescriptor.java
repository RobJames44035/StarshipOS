/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file, and Oracle licenses the original version of this file under the BSD
 * license:
 */
package jdk.dynalink;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Call site descriptors contain all the information necessary for linking a
 * call site. This information is normally passed as parameters to bootstrap
 * methods and consists of the {@code MethodHandles.Lookup} object on the caller
 * class in which the call site occurs, the dynamic operation at the call
 * site, and the method type of the call site. {@code CallSiteDescriptor}
 * objects are used in Dynalink to capture and store these parameters for
 * subsequent use by the {@link DynamicLinker}.
 * <p>
 * The constructors of built-in {@link RelinkableCallSite} implementations all
 * take a call site descriptor.
 * <p>
 * Call site descriptors must be immutable. You can use this class as-is or you
 * can subclass it, especially if you need to add further information to the
 * descriptors (typically, values passed in additional parameters to the
 * bootstrap method. Since the descriptors must be immutable, you can set up a
 * cache for equivalent descriptors to have the call sites share them.
 * <p>
 * The class extends {@link SecureLookupSupplier} to encapsulate the {@code
 * MethodHandles.Lookup} object. This lookup should be used
 * to find method handles to set as targets of the call site described by this
 * descriptor.
 * @since 9
 */
public class CallSiteDescriptor extends SecureLookupSupplier {
    private final Operation operation;
    private final MethodType methodType;

    /**
     * Creates a new call site descriptor.
     * @param lookup the lookup object describing the class the call site belongs to.
     * When creating descriptors from a {@link java.lang.invoke} bootstrap method,
     * it should be the lookup passed to the bootstrap.
     * @param operation the dynamic operation at the call site.
     * @param methodType the method type of the call site. When creating
     * descriptors from a {@link java.lang.invoke} bootstrap method, it should be
     * the method type passed to the bootstrap.
     */
    public CallSiteDescriptor(final Lookup lookup, final Operation operation, final MethodType methodType) {
        super(lookup);
        this.operation = Objects.requireNonNull(operation, "name");
        this.methodType = Objects.requireNonNull(methodType, "methodType");
    }

    /**
     * Returns the operation at the call site.
     * @return the operation at the call site.
     */
    public final Operation getOperation() {
        return operation;
    }

    /**
     * The type of the method at the call site.
     *
     * @return type of the method at the call site.
     */
    public final MethodType getMethodType() {
        return methodType;
    }

    /**
     * Finds or creates a call site descriptor that only differs in its
     * method type from this descriptor.
     * Invokes {@link #changeMethodTypeInternal(MethodType)}.
     *
     * @param newMethodType the new method type
     * @return a call site descriptor with changed method type.
     * @throws NullPointerException if {@code newMethodType} is null.
     */
    public final CallSiteDescriptor changeMethodType(final MethodType newMethodType) {
        final CallSiteDescriptor changed = changeMethodTypeInternal(newMethodType);

        if (getClass() != CallSiteDescriptor.class) {
            assertChangeInvariants(changed, "changeMethodTypeInternal");
            alwaysAssert(operation == changed.operation, () -> "changeMethodTypeInternal must not change the descriptor's operation");
            alwaysAssert(newMethodType == changed.methodType, () -> "changeMethodTypeInternal didn't set the correct new method type");
        }
        return changed;
    }

    /**
     * Finds or creates a call site descriptor that only differs in its
     * method type from this descriptor. Subclasses must override this method
     * to return an object of their exact class. If an overridden method changes
     * something other than the method type in the descriptor (its class, lookup,
     * or operation), or returns null, an {@code AssertionError} will be thrown
     * from {@link #changeMethodType(MethodType)}.
     *
     * @param newMethodType the new method type
     * @return a call site descriptor with the changed method type.
     */
    protected CallSiteDescriptor changeMethodTypeInternal(final MethodType newMethodType) {
        return new CallSiteDescriptor(getLookupPrivileged(), operation, newMethodType);
    }

    /**
     * Finds or creates a call site descriptor that only differs in its
     * operation from this descriptor.
     * Invokes {@link #changeOperationInternal(Operation)}.
     *
     * @param newOperation the new operation
     * @return a call site descriptor with the changed operation.
     * @throws NullPointerException if {@code newOperation} is null.
     */
    public final CallSiteDescriptor changeOperation(final Operation newOperation) {
        getLookup(); // force security check
        final CallSiteDescriptor changed = changeOperationInternal(newOperation);

        if (getClass() != CallSiteDescriptor.class) {
            assertChangeInvariants(changed, "changeOperationInternal");
            alwaysAssert(methodType == changed.methodType, () -> "changeOperationInternal must not change the descriptor's method type");
            alwaysAssert(newOperation == changed.operation, () -> "changeOperationInternal didn't set the correct new operation");
        }
        return changed;
    }

    /**
     * Finds or creates a call site descriptor that only differs in its
     * operation from this descriptor. Subclasses must override this method
     * to return an object of their exact class. If an overridden method changes
     * something other than the operation in the descriptor (its class, lookup,
     * or method type), or returns null, an {@code AssertionError} will be thrown
     * from {@link #changeOperation(Operation)}.
     *
     * @param newOperation the new operation
     * @return a call site descriptor with the changed operation.
     */
    protected CallSiteDescriptor changeOperationInternal(final Operation newOperation) {
        return new CallSiteDescriptor(getLookupPrivileged(), newOperation, methodType);
    }

    /**
     * Returns true if this call site descriptor is equal to the passed object.
     * It is considered equal if the other object is of the exact same class,
     * their operations and method types are equal, and their lookups have the
     * same {@link java.lang.invoke.MethodHandles.Lookup#lookupClass()} and
     * {@link java.lang.invoke.MethodHandles.Lookup#lookupModes()}.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj.getClass() != getClass()) {
            return false;
        }
        final CallSiteDescriptor other = (CallSiteDescriptor)obj;
        return operation.equals(other.operation) &&
               methodType.equals(other.methodType) &&
               lookupsEqual(getLookupPrivileged(), other.getLookupPrivileged());
    }

    /**
     * Compares two lookup objects for value-based equality. They are considered
     * equal if they have the same
     * {@link java.lang.invoke.MethodHandles.Lookup#lookupClass()} and
     * {@link java.lang.invoke.MethodHandles.Lookup#lookupModes()}.
     * @param l1 first lookup
     * @param l2 second lookup
     * @return true if the two lookups are equal, false otherwise.
     */
    private static boolean lookupsEqual(final Lookup l1, final Lookup l2) {
        return l1.lookupClass() == l2.lookupClass() && l1.lookupModes() == l2.lookupModes();
    }

    /**
     * Returns a value-based hash code of this call site descriptor computed
     * from its operation, method type, and lookup object's lookup class and
     * lookup modes.
     * @return value-based hash code for this call site descriptor.
     */
    @Override
    public int hashCode() {
        return operation.hashCode() + 31 * methodType.hashCode() + 31 * 31 * lookupHashCode(getLookupPrivileged());
    }

    /**
     * Returns a value-based hash code for the passed lookup object. It is
     * based on the lookup object's
     * {@link java.lang.invoke.MethodHandles.Lookup#lookupClass()} and
     * {@link java.lang.invoke.MethodHandles.Lookup#lookupModes()} values.
     * @param lookup the lookup object.
     * @return a hash code for the object..
     */
    private static int lookupHashCode(final Lookup lookup) {
        return lookup.lookupClass().hashCode() + 31 * lookup.lookupModes();
    }

    /**
     * Returns the string representation of this call site descriptor, of the
     * format {@code name(parameterTypes)returnType@lookup}.
     */
    @Override
    public String toString() {
        final String mt = methodType.toString();
        final String l = getLookupPrivileged().toString();
        final String o = operation.toString();
        return o + mt + '@' + l;
    }

    private void assertChangeInvariants(final CallSiteDescriptor changed, final String caller) {
        alwaysAssert(changed != null, () -> caller + " must not return null.");
        alwaysAssert(getClass() == changed.getClass(), () -> caller + " must not change the descriptor's class");
        alwaysAssert(lookupsEqual(getLookupPrivileged(), changed.getLookupPrivileged()), () -> caller + " must not change the descriptor's lookup");
    }

    private static void alwaysAssert(final boolean cond, final Supplier<String> errorMessage) {
        if (!cond) {
            throw new AssertionError(errorMessage.get());
        }
    }
}
