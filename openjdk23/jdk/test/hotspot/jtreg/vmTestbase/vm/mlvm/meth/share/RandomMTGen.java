/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

import java.lang.invoke.MethodType;

import vm.mlvm.share.Env;

public class RandomMTGen {

    private static final int MAX_ARGS = 10;

    public static MethodType generateRandomMT() {
        Class<?> rtype = RandomTypeGen.next();
        Class<?>[] ptypes = new Class<?>[Env.getRNG().nextInt(MAX_ARGS)];
        for ( int i = 0; i < ptypes.length; i++ ) {
            ptypes[i] = RandomTypeGen.next();
        }

        return MethodType.methodType(rtype, ptypes);
    }

}
