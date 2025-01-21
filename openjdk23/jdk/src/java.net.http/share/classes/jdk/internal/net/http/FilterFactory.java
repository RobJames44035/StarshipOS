/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.internal.net.http;

import java.util.ArrayList;
import java.util.List;

class FilterFactory {

    // Strictly-ordered list of filters.
    final List<Class<? extends HeaderFilter>> filterClasses = new ArrayList<>(3);

    public void addFilter(Class<? extends HeaderFilter> type) {
        filterClasses.add(type);
    }

    List<HeaderFilter> getFilterChain() {
        List<HeaderFilter> l = new ArrayList<>(filterClasses.size());
        for (Class<? extends HeaderFilter> clazz : filterClasses) {
            try {
                // Requires a public no arg constructor.
                HeaderFilter headerFilter = clazz.getConstructor().newInstance();
                l.add(headerFilter);
            } catch (ReflectiveOperationException e) {
                throw new InternalError(e);
            }
        }
        return l;
    }
}
