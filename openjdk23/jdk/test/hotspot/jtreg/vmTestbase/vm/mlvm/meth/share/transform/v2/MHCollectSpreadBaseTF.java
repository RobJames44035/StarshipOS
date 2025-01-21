/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public abstract class MHCollectSpreadBaseTF extends MHBasicUnaryTF {

    public MHCollectSpreadBaseTF(MHCall target) {
        super(target);
    }

    @Override
    protected MethodHandle computeInboundMH(MethodHandle targetMH) throws NoSuchMethodException, IllegalAccessException {
        return computeCollectorMH(computeSpreaderMH(targetMH), targetMH.type());
    }

    protected abstract MethodHandle computeCollectorMH(MethodHandle spreaderMH, MethodType targetType);
    protected abstract MethodHandle computeSpreaderMH(MethodHandle targetMH);

    @Override
    protected String getDescription() {
        return "";
    }
}
