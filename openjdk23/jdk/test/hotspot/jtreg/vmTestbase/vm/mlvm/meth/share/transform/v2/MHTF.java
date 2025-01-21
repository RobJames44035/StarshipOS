/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

public abstract class MHTF {

    public abstract MHCall[] getOutboundCalls();
    public abstract MHCall computeInboundCall() throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException;

    public abstract MHTF[] getSubTFs();

    @Override
    public final String toString() {
        return "MH " + getName() + " TF: " + getDescription();
    }

    protected abstract String getName();
    protected abstract String getDescription();
}
