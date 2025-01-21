/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package p;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Invokes I.m using reflection.
 */
public class Treflect {

    public static int test(I ii) throws Throwable {
        int accum = 0;
        Method m = I.class.getMethod("m");
        try {
            for (int j = 0; j < 100000; j++) {
                Object o = m.invoke(ii);
                accum += ((Integer) o).intValue();
            }
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
        return accum;
    }

    public static int test(I ii, byte b, char c, short s, int i, long l,
            Object o1, Object o2, Object o3, Object o4, Object o5, Object o6)
            throws Throwable {
        Method m = I.class.getMethod("m", Byte.TYPE, Character.TYPE,
                Short.TYPE, Integer.TYPE, Long.TYPE,
                Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class);
        int accum = 0;
        try {
            for (int j = 0; j < 100000; j++) {
                Object o = m.invoke(ii, b, c, s, i, l, o1, o2, o3, o4, o5, o6);
                accum += ((Integer) o).intValue();
            }
        } catch (InvocationTargetException ite) {
            throw ite.getCause();
        }
        return accum;
    }
}
