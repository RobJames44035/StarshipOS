/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 6323374
 * @run testng WrappedUnmodifiableCollections
 */

import java.util.*;
import java.util.function.Function;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


@Test
public class WrappedUnmodifiableCollections {

    private static <T,E extends T> void testWrapping(T collection, Function<T,E> wrapper) {
        var collection1 = wrapper.apply(collection);
        var collection2 = wrapper.apply(collection1);
        assertNotSame(collection, collection2);
        assertSame(collection1, collection2);
    }

    public void testUnmodifiableListsDontWrap() {
        List<List<?>> lists = List.of(List.of(), List.of(1,2,3), List.of(1),
                List.of(1,2,3,4,5,6),
                List.of(1,2,3).subList(0,1),
                new LinkedList<>(List.of(1,2,3)),
                new ArrayList<>(List.of(1,2,3)));

        for(List<?> list : lists) {
            testWrapping(list, Collections::unmodifiableList);
        }
    }

    public void testUnmodifiableCollectionsDontWrap() {
        Collection<?> list = List.of();
        testWrapping(list, Collections::unmodifiableCollection);
    }

    public void testUnmodifiableSetsDontWrap() {

        List<Set<?>> sets = List.of(new TreeSet<>(),
                                    Set.of(1, 2),
                                    Set.of(1,2,3,4,5,6));

        for (Set<?> set : sets) {
            testWrapping(set, Collections::unmodifiableSet);
        }

        TreeSet<?> treeSet = new TreeSet<>();

        //Collections.UnmodifiableSortedSet
        testWrapping((SortedSet<?>) treeSet, Collections::unmodifiableSortedSet);

        //Collections.UnmodifiableNavigableSet
        testWrapping((NavigableSet<?>) treeSet, Collections::unmodifiableNavigableSet);

    }

    public void testUnmodifiableMapsDontWrap() {
        TreeMap<?,?> treeMap = new TreeMap<>();

        List<Map<?,?>> maps = List.of(treeMap,
                Map.of(1,1),
                Map.of(1, 1, 2, 2, 3, 3, 4, 4));

        for (Map<?,?> map : maps) {
            testWrapping(map, Collections::unmodifiableMap);
        }

        //Collections.UnModifiableSortedMap
        testWrapping((SortedMap<?,?>) treeMap, Collections::unmodifiableSortedMap);

        //Collections.UnModifiableNavigableMap
        testWrapping((NavigableMap<?,?>) treeMap, Collections::unmodifiableNavigableMap);

    }

}
