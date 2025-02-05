package org.starship.sys

import org.starship.init.Init

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

    /**
     * Serialize this object to a byte array.
     * @return Serialized byte array representation of this object.
     */
    byte[] serialize() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream()
        ObjectOutputStream oos = new ObjectOutputStream(bos)
        try {
            oos.writeObject(this)
            oos.flush()
            return bos.toByteArray()
        } finally {
            oos.close()
            bos.close()
        }
    }

    /**
     * Perform any required actions after a change.
     */
    static void onChange() {
        // Notify D-Bus subscribers or other listeners here.
        Init.publishSystemResourcesUpdate()
    }

    // Example methods to demonstrate triggering onChange
    void addToProcessTable(String key, Process value) {
        processTable.put(key, value)
        onChange()
    }

    void removeFromProcessTable(String key) {
        processTable.remove(key)
        onChange()
    }

    void addToResourceTable(String key, Object value) {
        resourceTable.put(key, value)
        onChange()
    }

    void removeFromResourceTable(String key) {
        resourceTable.remove(key)
        onChange()
    }
}
