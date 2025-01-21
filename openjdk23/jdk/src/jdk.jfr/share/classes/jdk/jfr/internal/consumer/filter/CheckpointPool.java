/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jfr.internal.consumer.filter;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a constant pool in a checkpoint, both entries and type id
 */
final class CheckpointPool {
    private final List<PoolEntry> entries = new ArrayList<>();
    private final long typeId;

    public CheckpointPool(long typeId) {
        this.typeId = typeId;
    }

    public boolean isTouched() {
        for (var entry : entries) {
            if (entry.isTouched()) {
                return true;
            }
        }
        return false;
    }

    public long getTouchedCount() {
        int count = 0;
        for (var entry : entries) {
            if (entry.isTouched()) {
                count++;
            }
        }
        return count;
    }

    public void add(PoolEntry pe) {
        entries.add(pe);
    }

    public long getTypeId() {
        return typeId;
    }

    public List<PoolEntry> getEntries() {
        return entries;
    }
}