/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package jdk.vm.ci.runtime.test;

import jdk.vm.ci.meta.ResolvedJavaField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Context for field related tests.
 */
public class FieldUniverse extends TypeUniverse {

    public static final Map<Field, ResolvedJavaField> fields = new HashMap<>();

    {
        for (Class<?> c : classes) {
            for (Field f : c.getDeclaredFields()) {
                ResolvedJavaField field = metaAccess.lookupJavaField(f);
                fields.put(f, field);
            }
        }
    }
}
