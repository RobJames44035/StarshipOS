/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import vm.mlvm.meth.share.Argument;
import vm.mlvm.meth.share.Arguments;
import vm.mlvm.meth.share.TestTypes;

public abstract class MHCastTF extends MHBasicUnaryTF {

    protected final Argument[] newArgs;
    protected final Argument newRetVal;
    protected final MethodType newMT;

    protected MHCastTF(MHCall target, Class<?> newRetType, Class<?>[] newArgTypes) {
        super(target);

        Argument[] targetArgs = target.getArgs();

        if ( newArgTypes.length != targetArgs.length )
            throw new IllegalArgumentException("newArgTypes length (" + newArgTypes.length + ") should be equal to argument count (" + targetArgs.length + ")");

        this.newArgs = new Argument[newArgTypes.length];
        for ( int i = 0; i < this.newArgs.length; i++ ) {
            if ( ! TestTypes.canConvertType(targetArgs[i].getType(), this.newArgs[i].getType(), false) )
                throw new IllegalArgumentException("Can't convert argument #" + i + " from [" + targetArgs[i].getType() + " to [" + this.newArgs[i].getType());

            this.newArgs[i] = convert(targetArgs[i], newArgTypes[i], false);
        }

        this.newRetVal = convert(target.getRetVal(), newRetType, true);

        this.newMT = MethodType.methodType(this.newRetVal.getType(), Arguments.typesArray(this.newArgs));
    }

    protected abstract Argument convert(Argument argument, Class<?> newClass, boolean isRetType);

    @Override
    protected abstract MethodHandle computeInboundMH(MethodHandle targetMH) throws NoSuchMethodException, IllegalAccessException;

    @Override
    protected Argument[] computeInboundArgs(Argument[] targetArgs) {
        return this.newArgs;
    }

    @Override
    protected Argument computeRetVal(Argument targetRetVal) {
        return this.newRetVal;
    }

    @Override
    protected String getDescription() {
        return "newRetVal=[" + this.newRetVal + "]; newArgs=[" + this.newArgs + "]";
    }

}
