/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share.pool;

import jdk.test.lib.util.Pair;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This is a helper class that provides tests with methods
 */
public class PoolHelper extends MethodHolder {
    private static final List<Pair<Executable, Callable<?>>> METHODS;

    /**
     * Filters only those methods who belong to Klass or its internal class
     * Internal and named as "method" or is a constructor
     */
    public static final Predicate<Executable> METHOD_FILTER = executable -> {
        String methodName = executable.getName();
        String className = executable.getDeclaringClass().getName();
        return className.matches(".*(Klass)(\\$Internal)?") &&
                (methodName.equals("method") ||
                        methodName.equals(className)); // if method is <init>
    };

    static {
        METHODS = new ArrayList<>();
        List<MethodHolder> holders = new ArrayList<>();
        holders.add(new compiler.compilercontrol.share.pool.sub.Klass());
        holders.add(new compiler.compilercontrol.share.pool.sub.KlassDup());
        holders.add(new compiler.compilercontrol.share.pool.subpack.Klass());
        holders.add(new compiler.compilercontrol.share.pool.subpack.KlassDup());
        holders.add(new compiler.compilercontrol.share.pool.sub.Klass.Internal());
        holders.add(new compiler.compilercontrol.share.pool.subpack.KlassDup.Internal());
        for (MethodHolder holder : holders) {
            METHODS.addAll(holder.getAllMethods());
        }
    }

    /**
     * Gets all methods from the pool using specified filter
     *
     * @param filter method filter
     * @return pairs of Executable and appropriate Callable
     */
    public List<Pair<Executable, Callable<?>>> getAllMethods(
            Predicate<Executable> filter) {
        return getAllMethods().stream()
                .filter(pair -> filter.test(pair.first))
                .collect(Collectors.toList());
    }

    /**
     * Gets all methods from the pool
     *
     * @return pairs of Executable and appropriate Callable
     */
    @Override
    public List<Pair<Executable, Callable<?>>> getAllMethods() {
        return METHODS;
    }
}
