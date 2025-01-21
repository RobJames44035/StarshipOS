/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.lang.reflect.Field;

/**
 * Use core reflection and setAccessible(true) to access private field.
 */

public class Test2 {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.misc.Unsafe");
        Field f = c.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Object unsafe = f.get(null);
    }
}
