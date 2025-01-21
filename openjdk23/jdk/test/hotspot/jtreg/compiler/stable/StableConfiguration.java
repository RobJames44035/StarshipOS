/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.stable;

import jdk.test.whitebox.WhiteBox;

public class StableConfiguration {
    static final WhiteBox WB = WhiteBox.getWhiteBox();
    public static final boolean isStableEnabled;

    static {
        Boolean value = WB.getBooleanVMFlag("FoldStableValues");
        isStableEnabled = (value == null ? false : value);
        System.out.println("@Stable:         " + (isStableEnabled ? "enabled" : "disabled"));
    }
}
