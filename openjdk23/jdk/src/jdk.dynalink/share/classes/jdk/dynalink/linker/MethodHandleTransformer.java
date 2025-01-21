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
package jdk.dynalink.linker;

import java.lang.invoke.MethodHandle;
import jdk.dynalink.DynamicLinkerFactory;

/**
 * A generic interface describing operations that transform method handles.
 * Typical usage is for implementing
 * {@link DynamicLinkerFactory#setInternalObjectsFilter(MethodHandleTransformer)
 * internal objects filters}.
 * @since 9
 */
@FunctionalInterface
public interface MethodHandleTransformer {
    /**
     * Transforms a method handle.
     * @param target the method handle being transformed.
     * @return transformed method handle.
     */
    public MethodHandle transform(final MethodHandle target);
}
