/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.util.*;

/**
 * A SortedMap implementation that does not implement NavigableMap. Useful for
 * testing ReverseOrderSortedMapView. Underlying implementation provided by TreeMap.
 */
public class SimpleSortedMap<K,V> implements SortedMap<K,V> {
    final SortedMap<K,V> map;

    public SimpleSortedMap() {
        map = new TreeMap<>();
    }

    public SimpleSortedMap(Comparator<? super K> comparator) {
        map = new TreeMap<>(comparator);
    }

    public SimpleSortedMap(Map<? extends K,? extends V> m) {
        map = new TreeMap<>(m);
    }

    // ========== Object ==========

    public boolean equals(Object o) {
        return map.equals(o);
    }

    public int hashCode() {
        return map.hashCode();
    }

    public String toString() {
        return map.toString();
    }

    // ========== Map ==========

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set<Map.Entry<K,V>> entrySet() {
        return map.entrySet();
    }

    public V get(Object key) {
        return map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public void putAll(Map<? extends K,? extends V> m) {
        map.putAll(m);
    }

    public V remove(Object key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public Collection<V> values() {
        return map.values();
    }

    // ========== SortedMap ==========

    public Comparator<? super K> comparator() {
        return map.comparator();
    }

    public K firstKey() {
        return map.firstKey();
    }

    public SortedMap<K,V> headMap(K toKey) {
        return map.headMap(toKey);
    }

    public K lastKey() {
        return map.lastKey();
    }

    public SortedMap<K,V> subMap(K fromKey, K toKey) {
        return map.subMap(fromKey, toKey);
    }

    public SortedMap<K,V> tailMap(K fromKey) {
        return map.tailMap(fromKey);
    }
}
