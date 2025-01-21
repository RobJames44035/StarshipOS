/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea and Martin Buchholz with assistance from
 * members of JCP JSR-166 Expert Group and released to the public
 * domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.Map;

/** Allows tests to work with different Map implementations. */
public interface MapImplementation {
    /** Returns the Map implementation class. */
    public Class<?> klazz();
    /** Returns an empty map. */
    public Map emptyMap();

    // General purpose implementations can use Integers for key and value
    default Object makeKey(int i) { return i; }
    default Object makeValue(int i) { return i; }
    default int keyToInt(Object key) { return (Integer) key; }
    default int valueToInt(Object value) { return (Integer) value; }

    public boolean isConcurrent();
    default boolean remappingFunctionCalledAtMostOnce() { return true; };
    public boolean permitsNullKeys();
    public boolean permitsNullValues();
    public boolean supportsSetValue();
}
