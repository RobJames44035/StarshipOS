/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

/**
 * Pairwise transformation container
 */
public abstract class MHTFPair {

    protected final MHCall outboundTarget;

    protected MHTFPair(MHCall outboundTarget) {
        this.outboundTarget = outboundTarget;
    }

    public abstract MHTF getOutboundTF();
    public abstract MHTF getInboundTF(MHCall inboundTarget);
}
