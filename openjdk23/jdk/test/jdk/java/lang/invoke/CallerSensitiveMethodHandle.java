/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/* @test
 * @run testng/othervm CallerSensitiveMethodHandle
 * @summary Check Lookup findVirtual, findStatic and unreflect behavior with
 *          caller sensitive methods with focus on AccessibleObject.setAccessible
 */

import org.testng.annotations.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;

import static java.lang.invoke.MethodType.*;
import static org.testng.Assert.*;

public class CallerSensitiveMethodHandle {
    private static int field = 0;
    @Test
    public void privateField() throws Throwable {
        Lookup l = MethodHandles.lookup();
        Field f = CallerSensitiveMethodHandle.class.getDeclaredField("field");
        MethodHandle mh = l.findVirtual(Field.class, "setInt", methodType(void.class, Object.class, int.class));
        int newValue = 5;
        mh.invokeExact(f, (Object) null, newValue);
        assertTrue(field == newValue);
    }

    @Test
    public void lookupItself() throws Throwable {
        Lookup lookup = MethodHandles.lookup();
        MethodHandle MH_lookup2 = lookup.findStatic(MethodHandles.class, "lookup", methodType(Lookup.class));
        Lookup lookup2 = (Lookup) MH_lookup2.invokeExact();
        System.out.println(lookup2 + " original lookup class " + lookup.lookupClass());
        assertTrue(lookup2.lookupClass() == lookup.lookupClass());
    }
}
