/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package com.sun.tools.javac.comp;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Deduplication {
    void groupEquals(Object... xs) {}
    void groupNotEquals(Object... xs) {}
    void group(Object... xs) {}

    void test() {

        groupEquals(
                (Runnable) () -> { ( (Runnable) () -> {} ).run(); },
                (Runnable) () -> { ( (Runnable) () -> {} ).run(); }
        );

        groupEquals(
                (Runnable) () -> { Deduplication.class.toString(); },
                (Runnable) () -> { Deduplication.class.toString(); }
        );

        groupEquals(
                (Runnable) () -> { Integer[].class.toString(); },
                (Runnable) () -> { Integer[].class.toString(); }
        );

        groupEquals(
                (Runnable) () -> { char.class.toString(); },
                (Runnable) () -> { char.class.toString(); }
        );

        groupEquals(
                (Runnable) () -> { Void.class.toString(); },
                (Runnable) () -> { Void.class.toString(); }
        );

        groupEquals(
                (Runnable) () -> { void.class.toString(); },
                (Runnable) () -> { void.class.toString(); }
        );

        groupEquals((Function<String, Integer>) x -> x.hashCode());
        groupEquals((Function<Object, Integer>) x -> x.hashCode());

        {
            int x = 1;
            groupEquals((Supplier<Integer>) () -> x + 1);
        }
        {
            int x = 1;
            groupEquals((Supplier<Integer>) () -> x + 1);
        }
        groupEquals(
                (BiFunction<Integer, Integer, ?>) (x, y) -> x + ((y)),
                (BiFunction<Integer, Integer, ?>) (x, y) -> x + (y),
                (BiFunction<Integer, Integer, ?>) (x, y) -> x + y,
                (BiFunction<Integer, Integer, ?>) (x, y) -> (x) + ((y)),
                (BiFunction<Integer, Integer, ?>) (x, y) -> (x) + (y),
                (BiFunction<Integer, Integer, ?>) (x, y) -> (x) + y,
                (BiFunction<Integer, Integer, ?>) (x, y) -> ((x)) + ((y)),
                (BiFunction<Integer, Integer, ?>) (x, y) -> ((x)) + (y),
                (BiFunction<Integer, Integer, ?>) (x, y) -> ((x)) + y);

        groupEquals(
                (Function<Integer, Integer>) x -> x + (1 + 2 + 3),
                (Function<Integer, Integer>) x -> x + 6);

        groupEquals((Function<Integer, Integer>) x -> x + 1, (Function<Integer, Integer>) y -> y + 1);

        groupEquals((Consumer<Integer>) x -> this.f(), (Consumer<Integer>) x -> this.f());

        groupEquals((Consumer<Integer>) y -> this.g());

        groupEquals((Consumer<Integer>) x -> f(), (Consumer<Integer>) x -> f());

        groupEquals((Consumer<Integer>) y -> g());

        groupEquals((Function<Integer, Integer>) x -> this.i, (Function<Integer, Integer>) x -> this.i);

        groupEquals((Function<Integer, Integer>) y -> this.j);

        groupEquals((Function<Integer, Integer>) x -> i, (Function<Integer, Integer>) x -> i);

        groupEquals((Function<Integer, Integer>) y -> j);

        groupEquals(
                (Function<Integer, Integer>)
                        y -> {
                            while (true) {
                                break;
                            }
                            return 42;
                        },
                (Function<Integer, Integer>)
                        y -> {
                            while (true) {
                                break;
                            }
                            return 42;
                        });

        groupEquals(
                (Function<Integer, Integer>)
                        x -> {
                            int y = x;
                            return y;
                        },
                (Function<Integer, Integer>)
                        x -> {
                            int y = x;
                            return y;
                        });

        groupEquals(
                (Function<Integer, Integer>)
                        x -> {
                            int y = 0, z = x;
                            return y;
                        });
        groupEquals(
                (Function<Integer, Integer>)
                        x -> {
                            int y = 0, z = x;
                            return z;
                        });

        class Local {
            int i;

            void f() {}

            {
                groupEquals((Function<Integer, Integer>) x -> this.i);
                groupEquals((Consumer<Integer>) x -> this.f());
                groupEquals((Function<Integer, Integer>) x -> Deduplication.this.i);
                groupEquals((Consumer<Integer>) x -> Deduplication.this.f());
            }
        }

        groupEquals((Function<Integer, Integer>) x -> switch (x) { default: yield x; },
              (Function<Integer, Integer>) x -> switch (x) { default: yield x; });

        groupEquals((Function<Object, Integer>) x -> x instanceof Integer i ? i : -1,
              (Function<Object, Integer>) x -> x instanceof Integer i ? i : -1);

        groupEquals((Function<Object, Integer>) x -> x instanceof R(var i1, var i2) ? i1 : -1,
              (Function<Object, Integer>) x -> x instanceof R(var i1, var i2) ? i1 : -1 );

        groupEquals((Function<Object, Integer>) x -> x instanceof R(Integer i1, int i2) ? i2 : -1,
              (Function<Object, Integer>) x -> x instanceof R(Integer i1, int i2) ? i2 : -1 );

        groupEquals((Function<Object, Integer>) x -> x instanceof int i2 ? i2 : -1,
              (Function<Object, Integer>) x -> x instanceof int i2 ? i2 : -1);

        groupEquals((Function<Object, Integer>) x -> switch (x) { case String s -> s.length(); default -> -1; },
              (Function<Object, Integer>) x -> switch (x) { case String s -> s.length(); default -> -1; });

        groupEquals((Function<Object, Integer>) x -> {
                    int y1 = -1;
                    return y1;
                },
              (Function<Object, Integer>) x -> {
                    int y2 = -1;
                    return y2;
               });

        groupNotEquals((Function<Object, Integer>) x -> {class C {} new C(); return 42; }, (Function<Object, Integer>) x -> {class C {} new C(); return 42; });
    }

    void f() {}

    void g() {}

    int i;
    int j;

    record R(Integer i1, int i2) {}
}
