/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package com.sun.hotspot.igv.data;

import java.util.List;

public interface Folder extends Properties.Provider {
    void setName(String name);
    String getName();
    String getDisplayName();
    List<? extends FolderElement> getElements();
    void removeElement(FolderElement element);
    void addElement(FolderElement group);
    ChangedEvent<? extends Folder> getChangedEvent();
    Properties getProperties();
}
