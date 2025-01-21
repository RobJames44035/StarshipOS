/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.internal.foreign.abi.aarch64.linux;

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
 * ABI implementation based on ARM document "Procedure Call Standard for
 * the ARM 64-bit Architecture".
 */
public final class LinuxAArch64Linker extends AbstractLinker {

    static final Map<String, MemoryLayout> CANONICAL_LAYOUTS =
            SharedUtils.canonicalLayouts(ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT);

    public static LinuxAArch64Linker getInstance() {
        final class Holder {
            private static final LinuxAArch64Linker INSTANCE = new LinuxAArch64Linker();
        }

        return Holder.INSTANCE;
    }

    private LinuxAArch64Linker() {
        // Ensure there is only one instance
    }

    @Override
    protected MethodHandle arrangeDowncall(MethodType inferredMethodType, FunctionDescriptor function, LinkerOptions options) {
        return CallArranger.LINUX.arrangeDowncall(inferredMethodType, function, options);
    }

    @Override
    protected UpcallStubFactory arrangeUpcall(MethodType targetType, FunctionDescriptor function, LinkerOptions options) {
        return CallArranger.LINUX.arrangeUpcall(targetType, function, options);
    }

    @Override
    public Map<String, MemoryLayout> canonicalLayouts() {
        return CANONICAL_LAYOUTS;
    }
}
