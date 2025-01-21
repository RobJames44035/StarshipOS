/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.WrongMethodTypeException;
import java.util.Arrays;

import nsk.share.test.TestUtils;
import vm.mlvm.meth.share.Argument;
import vm.mlvm.meth.share.Arguments;
import vm.mlvm.meth.share.MHUtils;

public class MHInsertTF extends MHInsertOrDropTF {

    protected final boolean _canDropProtected;

    public MHInsertTF(MHCall target, int pos, Argument[] values, boolean canDropProtected) {
        super(target, pos, values);
        _canDropProtected = canDropProtected;
    }

    @Override
    protected void check(Argument[] targetArgs) throws IllegalArgumentException {
        super.check(targetArgs);

        for (int i = this.pos; i < Math.min(targetArgs.length, this.pos + this.values.length); i++) {
            if ( ! _canDropProtected && targetArgs[i].isPreserved() ) {
                throw new WrongMethodTypeException("Dropping a protected argument #" + i
                                                 + ": " + targetArgs[i]);

            }

            MHUtils.assertAssignableType("argument " + i, targetArgs[i].getType(), this.values[i - this.pos].getType());
        }
    }

    @Override
    protected Argument[] computeInboundArgs(Argument[] targetArgs) {
        return TestUtils.concatArrays(
                Arrays.copyOfRange(targetArgs, 0, this.pos),
                Arrays.copyOfRange(targetArgs, this.pos + this.values.length, targetArgs.length));
    }

    @Override
    protected MethodHandle computeInboundMH(MethodHandle targetMH) {
        return MethodHandles.insertArguments(targetMH, this.pos, Arguments.valuesArray(this.values));
    }

    @Override
    protected String getName() {
        return "insertArguments";
    }

}
