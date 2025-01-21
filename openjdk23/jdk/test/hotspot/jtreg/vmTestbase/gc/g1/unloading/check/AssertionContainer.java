/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.check;

import java.util.*;

/**
 * This is the storage for assertions. Here assertions are stored until required number of garbage collections happen.
 * This container isn't thread-safe.
 */
public class AssertionContainer {

    private SortedMap<Long, List<Assertion>> sortedMap = new TreeMap<>();

    /**
     * Enqueue assertion to storage.
     * @param assertions
     * @param gcCounter - gc counter value as of assertion created
     */
    public void enqueue(Collection<Assertion> assertions, Long gcCounter) {
        if (sortedMap.get(gcCounter) != null) {
            sortedMap.get(gcCounter).addAll(assertions);
        } else {
            List<Assertion> newList = new LinkedList<>();
            newList.addAll(assertions);
            sortedMap.put(gcCounter, newList);
        }
    }

    /**
     * Get assertions that are ready for check. That means they where created when gc counter was less then
     * specified value.
     * @param bound - value of gc counter
     * @return - collection of assertions. It can be empty if no assertions are mature yet.
     */
    public Collection<Assertion> getElder(Long bound) {
        Collection<Assertion> returnValue = new LinkedList<>();
        SortedMap<Long, List<Assertion>> filteredView = sortedMap.headMap(bound);
        for (Long l : filteredView.keySet()) {
            List<Assertion> list = filteredView.remove(l);
            returnValue.addAll(list);
        }
        return returnValue;
    }

}
