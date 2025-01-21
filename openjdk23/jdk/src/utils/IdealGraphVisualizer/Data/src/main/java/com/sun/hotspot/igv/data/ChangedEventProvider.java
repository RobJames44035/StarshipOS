/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */
package com.sun.hotspot.igv.data;

/**
 * Provides a changed event object.
 * @author Thomas Wuerthinger
 * @param <T> Class for which the changed event fires.
 */
public interface ChangedEventProvider<T> {

    /**
     * Returns the changed event object. Should always return the same instance.
     */
    ChangedEvent<T> getChangedEvent();
}
