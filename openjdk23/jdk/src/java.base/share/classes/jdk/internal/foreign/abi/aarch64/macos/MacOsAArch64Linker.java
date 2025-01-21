/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.internal.foreign.abi.aarch64.macos;

import jdk.internal.foreign.abi.AbstractLinker;
import jdk.internal.foreign.abi.LinkerOptions;
import jdk.internal.foreign.abi.SharedUtils;
import jdk.internal.foreign.abi.aarch64.CallArranger;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Map;

/**
 * ABI implementation for macOS on Apple Silicon. Based on AAPCS with
 * changes to va_list and passing arguments on the stack.
 */
public final class MacOsAArch64Linker extends AbstractLinker {

    static final Map<String, MemoryLayout> CANONICAL_LAYOUTS =
            SharedUtils.canonicalLayouts(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT);

    public static MacOsAArch64Linker getInstance() {
        final class Holder {
            private static final MacOsAArch64Linker INSTANCE = new MacOsAArch64Linker();
        }

        return Holder.INSTANCE;
    }

    private MacOsAArch64Linker() {
        // Ensure there is only one instance
    }

    @Override
    protected MethodHandle arrangeDowncall(MethodType inferredMethodType, FunctionDescriptor function, LinkerOptions options) {
        return CallArranger.MACOS.arrangeDowncall(inferredMethodType, function, options);
    }

    @Override
    protected UpcallStubFactory arrangeUpcall(MethodType targetType, FunctionDescriptor function, LinkerOptions options) {
        return CallArranger.MACOS.arrangeUpcall(targetType, function, options);
    }

    @Override
    public Map<String, MemoryLayout> canonicalLayouts() {
        return CANONICAL_LAYOUTS;
    }
}
