/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

public class RandomArgumentGen {

    public static Argument next() throws InstantiationException, IllegalAccessException {
        return next(RandomTypeGen.next());
    }

    public static Argument next(Class<?> type) throws InstantiationException, IllegalAccessException {
        return new Argument(type, RandomValueGen.next(type));
    }
}
