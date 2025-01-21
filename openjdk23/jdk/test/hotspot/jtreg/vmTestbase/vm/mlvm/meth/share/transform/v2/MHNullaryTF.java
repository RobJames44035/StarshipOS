/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

public abstract class MHNullaryTF extends MHPrimitiveTF {

    @Override
    public MHCall[] getOutboundCalls() {
        return new MHCall[0];
    }
}
