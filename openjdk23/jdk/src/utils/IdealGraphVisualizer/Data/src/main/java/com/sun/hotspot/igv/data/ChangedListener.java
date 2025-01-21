/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.data;

/**
 * Listens to changed events.
 * @author Thomas Wuerthinger
 * @param <T> Class for which the changed event fires.
 */
public interface ChangedListener<T> {

    /**
     * This method is called everytime a changed event is fired.
     * @param source Object that has changed.
     */
    void changed(T source);
}
