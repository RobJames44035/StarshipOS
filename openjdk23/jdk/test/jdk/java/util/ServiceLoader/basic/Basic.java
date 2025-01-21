/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

//

import java.io.*;
import java.util.*;


public class Basic {

    private static PrintStream out = System.err;

    private static <T> Set<T> setOf(Iterable<T> it) {
        Set<T> s = new HashSet<T>();
        for (T t : it)
            s.add(t);
        return s;
    }

    private static <T> void checkEquals(Set<T> s1, Set<T> s2, boolean eq) {
        if (s1.equals(s2) != eq)
            throw new RuntimeException(String.format("%b %s : %s",
                                                     eq, s1, s2));
    }

    abstract static class TestLoader {
        String name;

        TestLoader(String name) { this.name = name; }

        abstract ServiceLoader<FooService> load();
    }

    static TestLoader tcclLoader = new TestLoader("Thread context class loader") {
        ServiceLoader<FooService> load() {
            return ServiceLoader.load(FooService.class);
        }
    };

    static TestLoader systemClLoader = new TestLoader("System class loader") {
        ServiceLoader<FooService> load() {
            return ServiceLoader.load(FooService.class, ClassLoader.getSystemClassLoader());
        }
    };

    static TestLoader nullClLoader = new TestLoader("null (defer to system class loader)") {
        ServiceLoader<FooService> load() {
            return ServiceLoader.load(FooService.class, null);
        }
    };

    public static void main(String[] args) {
        for (TestLoader tl : Arrays.asList(tcclLoader, systemClLoader, nullClLoader)) {
            test(tl);
        }
    }

    static void test(TestLoader tl) {
        ServiceLoader<FooService> sl = tl.load();
        out.format("%s: %s%n", tl.name, sl);

        // Providers are cached
        Set<FooService> ps = setOf(sl);
        checkEquals(ps, setOf(sl), true);

        // The cache can be flushed and reloaded
        sl.reload();
        checkEquals(ps, setOf(sl), false);

    }
}
