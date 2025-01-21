/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.util.*;

/**
 * A complete Deque implementation that inherits the reversed() method
 * from SequencedCollection. Useful for testing ReverseOrderDequeView.
 * Underlying implementation provided by ArrayDeque.
 */
public class SimpleDeque<E> implements Deque<E> {

    final Deque<E> deque;

    public SimpleDeque() {
        deque = new ArrayDeque<>();
    }

    public SimpleDeque(Collection<? extends E> c) {
        deque = new ArrayDeque<>(c);
    }

    // ========== Object ==========

    public boolean equals(Object o) {
        return deque.equals(o);
    }

    public int hashCode() {
        return deque.hashCode();
    }

    public String toString() {
        return deque.toString();
    }

    // ========== Collection ==========

    public boolean add(E e) {
        return deque.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        return deque.addAll(c);
    }

    public void clear() {
        deque.clear();
    }

    public boolean contains(Object o) {
        return deque.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return deque.containsAll(c);
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public Iterator<E> iterator() {
        return deque.iterator();
    }

    public boolean remove(Object o) {
        return deque.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return deque.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return deque.retainAll(c);
    }

    public int size() {
        return deque.size();
    }

    public Object[] toArray() {
        return deque.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return deque.toArray(a);
    }

    // ========== Deque ==========

    public void addFirst(E e) {
        deque.addFirst(e);
    }

    public void addLast(E e) {
        deque.addLast(e);
    }

    public boolean offerFirst(E e) {
        return deque.offerFirst(e);
    }

    public boolean offerLast(E e) {
        return deque.offerLast(e);
    }

    public E removeFirst() {
        return deque.removeFirst();
    }

    public E removeLast() {
        return deque.removeLast();
    }

    public E pollFirst() {
        return deque.pollFirst();
    }

    public E pollLast() {
        return deque.pollLast();
    }

    public E getFirst() {
        return deque.getFirst();
    }

    public E getLast() {
        return deque.getLast();
    }

    public E peekFirst() {
        return deque.peekFirst();
    }

    public E peekLast() {
        return deque.peekLast();
    }

    public boolean removeFirstOccurrence(Object o) {
        return deque.removeFirstOccurrence(o);
    }

    public boolean removeLastOccurrence(Object o) {
        return deque.removeLastOccurrence(o);
    }

    public boolean offer(E e) {
        return deque.offer(e);
    }

    public E remove() {
        return deque.remove();
    }

    public E poll() {
        return deque.poll();
    }

    public E element() {
        return deque.element();
    }

    public E peek() {
        return deque.peek();
    }

    public void push(E e) {
        deque.push(e);
    }

    public E pop() {
        return deque.pop();
    }

    public Iterator<E> descendingIterator() {
        return deque.descendingIterator();
    }
}
