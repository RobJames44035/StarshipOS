/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

import java.util.Map;

/**
 * Map-like interface for storing String-value pairs.
 *
 * @param <V> type of values stored in the map
 */
public interface OptionNameMap<V> {
    boolean contains( String key );

    V get( String key );

    void put( String key, V newValue );

    void putAll( Iterable<String> keys, V newValue );

    void remove( String key );

    Map<String, V> toJavaUtilMap();
}
