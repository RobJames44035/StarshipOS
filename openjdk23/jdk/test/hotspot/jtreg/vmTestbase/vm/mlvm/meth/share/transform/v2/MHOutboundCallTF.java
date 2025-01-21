/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;

import vm.mlvm.meth.share.Argument;

public class MHOutboundCallTF extends MHNullaryTF {

    private final Argument retVal;
    private final MethodHandle mh;
    private final Argument[] args;

    public MHOutboundCallTF(Argument retVal, MethodHandle mh, Argument[] args) {
        this.retVal = retVal;
        this.mh = mh;
        this.args = args;
    }

    @Override
    protected void check() throws IllegalArgumentException {
    }

    @Override
    protected Argument computeRetVal() {
        return this.retVal;
    }

    @Override
    protected Argument[] computeInboundArgs() {
        return this.args;
    }

    @Override
    protected MethodHandle computeInboundMH() {
        return this.mh;
    }

    @Override
    protected String getName() {
        return "outboundCall";
    }

    @Override
    protected String getDescription() {
        return "retVal=" + this.retVal + "; mh=" + this.mh + "; args=" + Arrays.toString(this.args);
    }
}
