package org.starship.sys

import java.util.concurrent.ConcurrentHashMap

class SystemResources implements Serializable {
    private static final long serialVersionUID = 1L

    ConcurrentHashMap<String, Object> processTable = new ConcurrentHashMap<>()
    ConcurrentHashMap<String, Object> resourceTable = new ConcurrentHashMap<>()
    ConcurrentHashMap<String, Object> serviceTable = new ConcurrentHashMap<>()

    private static final SystemResources INSTANCE = new SystemResources()

    private SystemResources() {
        // Private constructor to enforce singleton
    }

    static SystemResources getInstance() {
        return INSTANCE
    }
}
