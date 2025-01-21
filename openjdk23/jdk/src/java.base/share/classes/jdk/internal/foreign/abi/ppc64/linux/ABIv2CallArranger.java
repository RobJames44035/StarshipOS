/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.foreign.abi.ppc64.linux;

import jdk.internal.foreign.abi.ppc64.CallArranger;

/**
 * PPC64 CallArranger specialized for ABI v2.
 */
public class ABIv2CallArranger extends CallArranger {

    @Override
    protected boolean useABIv2() {
        return true;
    }

    @Override
    protected boolean isAIX() {
        return false;
    }
}
