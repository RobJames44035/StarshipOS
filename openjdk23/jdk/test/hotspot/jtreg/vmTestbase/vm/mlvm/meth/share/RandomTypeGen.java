/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

import vm.mlvm.share.Env;

public class RandomTypeGen {

    public static Class<?> next() {
        return TestTypes.TYPES[Env.getRNG().nextInt(TestTypes.TYPES.length)];
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            System.out.println(next());
    }

}
