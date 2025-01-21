/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package com.sun.hotspot.igv.util;

import com.sun.hotspot.igv.data.ChangedListener;
import com.sun.hotspot.igv.data.Event;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author Thomas
 */
public class LookupHistory {

    private static final Map<Class, LookupHistoryImpl> cache = new HashMap<>();

    private static class LookupHistoryImpl<T> extends Event<ChangedListener<T>> implements LookupListener {

        private final Class<T> klass;
        private final Result<T> result;
        private T last;

        public LookupHistoryImpl(Class<T> klass) {
            this.klass = klass;
            result = Utilities.actionsGlobalContext().lookupResult(klass);
            result.addLookupListener(this);
            last = Utilities.actionsGlobalContext().lookup(klass);
            fire();
        }

        public T getLast() {
            return last;
        }

        @Override
        protected void fire(ChangedListener<T> l) {
            l.changed(last);
        }


        @Override
        public void resultChanged(LookupEvent ev) {
            T current = Utilities.actionsGlobalContext().lookup(klass);
            if (current != null) {
                last = current;
                this.fire();
            }
        }
    }

    public static <T> void init(Class<T> klass) {
        if (!cache.containsKey(klass)) {
            cache.put(klass, new LookupHistoryImpl<>(klass));
        }
    }

    public static <T> void terminate(Class<T> klass) {
        if (cache.containsKey(klass)) {
            cache.get(klass).fire();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getLast(Class<T> klass) {
        init(klass);
        assert cache.containsKey(klass);
        return (T) cache.get(klass).getLast();
    }

    @SuppressWarnings("unchecked")
    public static <T> void addListener(Class<T> klass, ChangedListener<T> listener) {
        init(klass);
        assert cache.containsKey(klass);
        cache.get(klass).addListener(listener);
        cache.get(klass).fire();
    }

    @SuppressWarnings("unchecked")
    public static <T> void removeListener(Class<T> klass, ChangedListener<T> listener) {
        if (cache.containsKey(klass)) {
            cache.get(klass).removeListener(listener);
        }
    }
}
