/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * @test
 * @bug 8340572
 * @summary ConcurrentModificationException when sorting ArrayList sublists
 */

public class SortingModCount {
    public static void main(String[] args) {
        testSortingSubListsDoesNotIncrementModCount();
        testSortingListDoesIncrementModCount();
    }

    private static void testSortingSubListsDoesNotIncrementModCount() {
        List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4));
        var a = l.subList(0, 2);
        var b = l.subList(2, 4);
        Collections.sort(a);
        Collections.sort(b);
    }

    private static void testSortingListDoesIncrementModCount() {
        List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4));
        var b = l.subList(2, 4);
        Collections.sort(l);
        try {
            Collections.sort(b);
            throw new Error("expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
