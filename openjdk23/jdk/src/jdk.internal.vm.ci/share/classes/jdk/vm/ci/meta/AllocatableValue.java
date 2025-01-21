/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package jdk.vm.ci.meta;

/**
 * Common base class for values that are stored in some location that's managed by the register
 * allocator (e.g. register, stack slot).
 */
public abstract class AllocatableValue extends Value implements JavaValue {

    public static final AllocatableValue[] NONE = {};

    public AllocatableValue(ValueKind<?> kind) {
        super(kind);
    }
}
