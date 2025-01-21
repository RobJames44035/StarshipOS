/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share.transform.v2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.WrongMethodTypeException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nsk.share.test.TestUtils;

import vm.mlvm.meth.share.Argument;
import vm.mlvm.meth.share.MHUtils;

public class MHFilterTF extends MHNaryTF {

    protected final MHCall _target, _filters[];
    protected final int _pos;

    public MHFilterTF(MHCall target, int pos, MHCall[] filters) {
        _target = target;
        _pos = pos;
        _filters = filters;
    }

    @Override
    protected void check() throws IllegalArgumentException {
        Argument[] targetArgs = _target.getArgs();
        for ( int i = 0; i < _filters.length; i++ ) {
            MHCall f = _filters[i];
            if ( f == null )
                continue;

            int p = i + _pos;

            if ( f.getArgs().length != 1 )
                throw new WrongMethodTypeException("Filter " + i + " should have exactly one argument, but has: " + f.getArgs());

            MHUtils.assertAssignableType("filter return type to target parameter " + i,
                    targetArgs[p].getType(),
                    f.getRetVal().getType());
        }
    }

    @Override
    protected Argument computeRetVal() {
        return _target.getRetVal();
    }

    @Override
    protected Argument[] computeInboundArgs() {
        Argument[] result = _target.getArgs().clone();

        for ( int i = 0; i < _filters.length; i++ ) {
            MHCall f = _filters[i];
            if ( f != null )
                result[i + _pos] = f.getArgs()[0];
        }

        return result;
    }

    @Override
    protected MethodHandle computeInboundMH() {
        MethodHandle[] filterMHs = new MethodHandle[_filters.length];
        for ( int i = 0; i < _filters.length; i++ ) {
            MHCall f = _filters[i];
            if ( f != null )
                filterMHs[i] = f.getTargetMH();
        }
        return MethodHandles.filterArguments(_target.getTargetMH(), _pos, filterMHs);
    }

    @Override
    public MHCall[] getOutboundCalls() {
        Set<MHCall> calls = new HashSet<MHCall>();
        calls.add(_target);
        calls.addAll(Arrays.asList(_filters));
        calls.remove(null);
        return calls.toArray(new MHCall[0]);
    }

    @Override
    protected String getName() {
        return "filterArguments";
    }

    @Override
    protected String getDescription() {
        return "pos=" + _pos + "; filters=" + Arrays.toString(_filters);
    }
}
