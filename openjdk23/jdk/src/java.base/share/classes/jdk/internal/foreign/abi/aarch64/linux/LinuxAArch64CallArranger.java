/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.internal.foreign.abi.aarch64.linux;

import jdk.internal.foreign.abi.ABIDescriptor;
import jdk.internal.foreign.abi.aarch64.CallArranger;

/**
 * AArch64 CallArranger specialized for Linux ABI.
 */
public class LinuxAArch64CallArranger extends CallArranger {

    @Override
    protected boolean varArgsOnStack() {
        // Variadic arguments are passed as normal arguments
        return false;
    }

    @Override
    protected boolean requiresSubSlotStackPacking() {
        return false;
    }

    @Override
    protected ABIDescriptor abiDescriptor() {
        return C;
    }

    @Override
    protected boolean useIntRegsForVariadicFloatingPointArgs() {
        return false;
    }

    @Override
    protected boolean spillsVariadicStructsPartially() {
        return false;
    }

}
