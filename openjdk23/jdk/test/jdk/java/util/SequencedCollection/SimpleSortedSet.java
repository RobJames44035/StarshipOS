/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.util.*;

/**
 * A SortedSet implementation that does not implement NavigableSet. Useful for
 * testing ReverseOrderSortedSetView. Underlying implementation provided by TreeSet.
 */
public class SimpleSortedSet<E> implements SortedSet<E> {

    final SortedSet<E> set;

    public SimpleSortedSet() {
        set = new TreeSet<E>();
    }

    public SimpleSortedSet(Collection<? extends E> c) {
        set = new TreeSet<>(c);
    }

    public SimpleSortedSet(Comparator<? super E> comparator) {
        set = new TreeSet<>(comparator);
    }

    // ========== Object ==========

    public boolean equals(Object o) {
        return set.equals(o);
    }

    public int hashCode() {
        return set.hashCode();
    }

    public String toString() {
        return set.toString();
    }

    // ========== Collection ==========

    public boolean add(E e) {
        return set.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        return set.addAll(c);
    }

    public void clear() {
        set.clear();
    }

    public boolean contains(Object o) {
        return set.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public Iterator<E> iterator() {
        return set.iterator();
    }

    public boolean remove(Object o) {
        return set.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return set.retainAll(c);
    }

    public int size() {
        return set.size();
    }

    public Object[] toArray() {
        return set.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return set.toArray(a);
    }

    // ========== SortedSet ==========

    public Comparator<? super E> comparator() {
        return set.comparator();
    }

    public E first() {
        return set.first();
    }

    public SortedSet<E> headSet(E toElement) {
        return set.headSet(toElement);
    }

    public E last() {
        return set.last();
    }

    public SortedSet<E> subSet(E fromElement, E toElement) {
        return set.subSet(fromElement, toElement);
    }

    public SortedSet<E> tailSet(E fromElement) {
        return set.tailSet(fromElement);
    }
}
