/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * Base test class.
 */
public abstract class AbstractTest {

    protected static boolean isIsoLeap(long year) {
        if (year % 4 != 0) {
            return false;
        }
        if (year % 100 == 0 && year % 400 != 0) {
            return false;
        }
        return true;
    }

    protected static void assertImmutable(Class<?> cls) {
        assertImmutable(cls, Set.of());
    }

    protected static void assertImmutable(Class<?> cls, Set<String> ignoreFields) {
        assertTrue(Modifier.isPublic(cls.getModifiers()));
        assertTrue(Modifier.isFinal(cls.getModifiers()));
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().contains("$") && !ignoreFields.contains(field.getName())) {
                if (Modifier.isStatic(field.getModifiers())) {
                    assertTrue(Modifier.isFinal(field.getModifiers()), "Field:" + field.getName());
                } else {
                    assertTrue(Modifier.isPrivate(field.getModifiers()), "Field:" + field.getName());
                    assertTrue(Modifier.isFinal(field.getModifiers()), "Field:" + field.getName());
                }
            }
        }
        Constructor<?>[] cons = cls.getDeclaredConstructors();
        for (Constructor<?> con : cons) {
            assertTrue(Modifier.isPrivate(con.getModifiers()));
        }
    }

}
