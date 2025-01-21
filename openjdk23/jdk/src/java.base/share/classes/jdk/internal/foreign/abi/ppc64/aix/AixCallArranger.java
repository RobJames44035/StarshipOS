/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.foreign.abi.ppc64.aix;

import jdk.internal.foreign.abi.ppc64.CallArranger;

/**
 * PPC64 CallArranger specialized for AIX.
 */
public class AixCallArranger extends CallArranger {

    @Override
    protected boolean useABIv2() {
        return false;
    }

    @Override
    protected boolean isAIX() {
        return true;
    }
}
