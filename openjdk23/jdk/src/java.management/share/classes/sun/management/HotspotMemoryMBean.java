/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.management;

import sun.management.counter.Counter;
/**
 * Hotspot internal management interface for the compilation system.
 */
public interface HotspotMemoryMBean {

    /**
     * Returns a list of internal counters maintained in the Java
     * virtual machine for the memory system.
     *
     * @return a list of internal counters maintained in the VM
     * for the memory system.
     */
    public java.util.List<Counter> getInternalMemoryCounters();
}
