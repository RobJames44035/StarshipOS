/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.java.lang.foreign.pointers;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;

public sealed abstract class NativeType<X> {
    public abstract MemoryLayout layout();

    public non-sealed static abstract class OfInt<X> extends NativeType<X> {
        public abstract ValueLayout.OfInt layout();
    }
    public non-sealed static abstract class OfDouble<X> extends NativeType<X> {
        public abstract ValueLayout.OfDouble layout();
    }

    private static Linker LINKER = Linker.nativeLinker();

    /**
     * The layout for the {@code int} C type
     */
    private static final ValueLayout.OfInt CANONICAL_INT = (ValueLayout.OfInt) LINKER.canonicalLayouts().get("int");
    /**
     * The layout for the {@code double} C type
     */
    private static final ValueLayout.OfDouble CANONICAL_DOUBLE = (ValueLayout.OfDouble) LINKER.canonicalLayouts().get("double");


    private static final AddressLayout UNSAFE_ADDRESS = ValueLayout.ADDRESS
            .withTargetLayout(MemoryLayout.sequenceLayout(Long.MAX_VALUE, ValueLayout.JAVA_BYTE));

    public final static class OfPointer<X> extends NativeType<X> {
        public AddressLayout layout() {
            return UNSAFE_ADDRESS;
        }
    }

    public non-sealed static abstract class OfStruct<X> extends NativeType<X> {
        public abstract GroupLayout layout();
        public abstract X make(Pointer<X> ptr);
    }

    public static final OfInt<Integer> C_INT = new OfInt<>() {
        @Override
        public ValueLayout.OfInt layout() {
            return CANONICAL_INT;
        }
    };

    public static final OfDouble<Double> C_DOUBLE = new OfDouble<>() {
        @Override
        public ValueLayout.OfDouble layout() {
            return CANONICAL_DOUBLE;
        }
    };

    @SuppressWarnings("unchecked")
    final static OfPointer C_VOID_PTR = new OfPointer();

    @SuppressWarnings("unchecked")
    public static final OfPointer<Pointer<Integer>> C_INT_PTR = NativeType.C_VOID_PTR;
    @SuppressWarnings("unchecked")
    public static final OfPointer<Pointer<Double>> C_DOUBLE_PTR = NativeType.C_VOID_PTR;



    @SuppressWarnings("unchecked")
    public static <Z> OfPointer<Pointer<Z>> ptr(NativeType<Z> type) {
        return NativeType.C_VOID_PTR;
    }
}
