/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file, and Oracle licenses the original version of this file under the BSD
 * license:
 */
package jdk.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class BeanIntrospector extends FacetIntrospector {
    private final Class<?> clazz;

    BeanIntrospector(final Class<?> clazz) {
        super(clazz, true);
        this.clazz = clazz;
    }

    @Override
    Map<String, MethodHandle> getInnerClassGetters() {
        return Map.of(); // NOTE: non-static inner classes are also on StaticClassIntrospector.
    }

    @Override Collection<Method> getRecordComponentGetters() {
        if (clazz.isRecord()) {
            final RecordComponent[] rcs = clazz.getRecordComponents();
            return Arrays.stream(rcs)
                .map(RecordComponent::getAccessor)
                .map(membersLookup::getAccessibleMethod)
                .filter(Objects::nonNull) // no accessible counterpart
                .toList();
        } else {
            return List.of();
        }
    }

    @Override
    MethodHandle editMethodHandle(final MethodHandle mh) {
        return mh;
    }
}
