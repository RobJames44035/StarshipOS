/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
import java.util.HashSet;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @library
 *
 * A simple mutable set implementation that provides only default
 * implementations of all methods. ie. none of the Set interface default methods
 * have overridden implementations.
 *
 * @param <E> type of set members
 */
public class ExtendsAbstractSet<E> extends AbstractSet<E> {

    protected final Set<E> set;

    public ExtendsAbstractSet() {
        this(HashSet<E>::new);
    }

    public ExtendsAbstractSet(Collection<E> source) {
        this();
        addAll(source);
    }

    protected ExtendsAbstractSet(Supplier<Set<E>> backer) {
        this.set = backer.get();
    }

    public boolean add(E element) {
        return set.add(element);
    }

    public boolean remove(Object element) {
        return set.remove(element);
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Iterator<E> source = set.iterator();

            public boolean hasNext() {
                return source.hasNext();
            }

            public E next() {
                return source.next();
            }

            public void remove() {
                source.remove();
            }
        };
    }

    public int size() {
        return set.size();
    }
}
