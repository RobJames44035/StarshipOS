package org.starship.sys

import java.util.concurrent.ConcurrentHashMap

class SystemResourceTables implements Serializable {
    private ConcurrentHashMap<String, Object> processTable = new ConcurrentHashMap<>()
    private ConcurrentHashMap<String, Object> resourceTable = new ConcurrentHashMap<>()
    static ConcurrentHashMap<String, Object> serviceTable = new ConcurrentHashMap<>()


}
