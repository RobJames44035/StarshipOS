/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package com.sun.xml.internal.stream;

/**
 * XMLBufferListerner should be implemented by classes which wish to receive
 * call backs from XMLEntityReader.
 *
 * @author k venugopal,
 * @author Neeraj bajaj
 */
public interface XMLBufferListener {

    /**
     * Will be invoked by XMLEntityReader before it tries to resize,load new data
     * into current ScannedEntities buffer.
     */
    public void refresh();

    /**
     * receives callbacks from {@link XMLEntityReader } when buffer
     * is being changed.
     * @param refreshPosition
     */
    public void refresh(int loadPosition);

}
