/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package p4;

import java.lang.reflect.Constructor;

public class C {
    public static Object tryNewInstance(Class<?> clazz) throws Exception {
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        return ctor.newInstance();
    }
}
