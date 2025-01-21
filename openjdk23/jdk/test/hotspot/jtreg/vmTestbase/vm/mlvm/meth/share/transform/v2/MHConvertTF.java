/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import vm.mlvm.meth.share.Argument;
import vm.mlvm.meth.share.TestTypes;

public class MHConvertTF extends MHCastTF {

    protected MHConvertTF(MHCall target, Class<?> newRetType, Class<?>[] newArgTypes) {
        super(target, newRetType, newArgTypes);
    }

    @Override
    protected Argument convert(Argument argument, Class<?> newClass, boolean isRetType) {
        return TestTypes.convertArgument(argument, newClass, isRetType);
    }

    @Override
    protected MethodHandle computeInboundMH(MethodHandle targetMH) throws NoSuchMethodException, IllegalAccessException {
        throw new RuntimeException("Internal error: Functionality disabled in JDK7");
        /*
        return MethodHandles.convertArguments(targetMH, this.newMT);
        */
    }

    @Override
    protected String getName() {
        return "convertArguments";
    }
}
