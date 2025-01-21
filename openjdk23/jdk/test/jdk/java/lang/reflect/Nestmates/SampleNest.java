/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

// A sample nest for use with reflection API tests
public class SampleNest {

    // recursively gather all the named nested types

    static List<Class<?>> _nestedTypes = new LinkedList<>();

    static void gather(Class<?> c) {
        _nestedTypes.add(c);
        for (Class<?> d : c.getDeclaredClasses()) {
            gather(d);
        }
    }

    static {
        gather(SampleNest.class);
        SampleNest s = new SampleNest();
    }

    public static Class<?>[] nestedTypes() {
        return _nestedTypes.toArray(new Class<?>[0]);
    }

    // Define a nested type of each possible kind

    static class StaticClass { }
    static interface StaticIface { }
    class InnerClass { }
    interface InnerIface { }

    // check multi-level nesting

    static class DeepNest1 {
        static class DeepNest2 {
            static class DeepNest3 {
            }
        }
    }

    // local and anonymous classes aren't declared
    // so they have to add themselves
    public SampleNest() {
        class LocalClass { }
        _nestedTypes.add(LocalClass.class);

        Runnable r = new Runnable() {
                public void run() {
                    // anonymous class
                    _nestedTypes.add(getClass());
                }
            };
        r.run();
    }
}

