/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filter;

import com.sun.hotspot.igv.data.ChangedEvent;
import com.sun.hotspot.igv.data.ChangedEventProvider;
import com.sun.hotspot.igv.data.ChangedListener;
import com.sun.hotspot.igv.graph.Diagram;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Thomas Wuerthinger
 */
public class FilterChain implements ChangedEventProvider<FilterChain> {

    private final List<Filter> filters;
    private final transient ChangedEvent<FilterChain> changedEvent;
    private final String name;

    private final ChangedListener<Filter> changedListener = new ChangedListener<Filter>() {
        @Override
        public void changed(Filter source) {
            changedEvent.fire();
        }
    };

    public FilterChain(String name) {
        this.name = name;
        filters = new ArrayList<>();
        changedEvent = new ChangedEvent<>(this);
    }

    public FilterChain() {
        this("");
    }

    public void sortBy(List<String> order) {
        filters.sort(Comparator.comparingInt(f -> order.indexOf(f.getName())));
    }

    @Override
    public ChangedEvent<FilterChain> getChangedEvent() {
        return changedEvent;
    }

    public void applyInOrder(Diagram diagram, FilterChain filterOrder) {
        for (Filter filter : filterOrder.getFilters()) {
            if (filters.contains(filter)) {
                filter.apply(diagram);
            }
        }
    }

    public void addFilter(Filter filter) {
        assert filter != null;
        filters.add(filter);
        filter.getChangedEvent().addListener(changedListener);
        changedEvent.fire();
    }

    public boolean containsFilter(Filter filter) {
        return filters.contains(filter);
    }

    public void clearFilters() {
        for (Filter filter : filters) {
            filter.getChangedEvent().removeListener(changedListener);
        }
        filters.clear();
        changedEvent.fire();
    }

    public void removeFilter(Filter filter) {
        assert filters.contains(filter);
        filters.remove(filter);
        filter.getChangedEvent().removeListener(changedListener);
        changedEvent.fire();
    }

    public void moveFilterUp(Filter filter) {
        assert filters.contains(filter);
        int index = filters.indexOf(filter);
        if (index != 0) {
            filters.remove(index);
            filters.add(index - 1, filter);
        }
        changedEvent.fire();
    }

    public void moveFilterDown(Filter filter) {
        assert filters.contains(filter);
        int index = filters.indexOf(filter);
        if (index != filters.size() - 1) {
            filters.remove(index);
            filters.add(index + 1, filter);
        }
        changedEvent.fire();
    }

    public void addFilters(List<Filter> filtersToAdd) {
        for (Filter filter : filtersToAdd) {
            addFilter(filter);
        }
    }

    public List<Filter> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
