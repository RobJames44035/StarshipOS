/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package gc.g1.plab.lib;

/**
 * The MemoryConsumer is used for consuming different amount of memory.
 * Class will store not more than 'capacity' number of objects with 'chunk' size.
 * If we exceed capacity, object will be stored at existing entries,
 * all previously added objects will be overwritten.
 * If capacity=1, only last object will be saved.
 */
public class MemoryConsumer {

    private int capacity;
    private int chunk;

    private Object[] array;
    private int index;

    /**
     * Create MemoryConsumer object with defined capacity
     *
     * @param capacity
     * @param chunk
     */
    public MemoryConsumer(int capacity, int chunk) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Items number should be greater than 0.");
        }
        if (chunk <= 0) {
            throw new IllegalArgumentException("Chunk size should be greater than 0.");
        }
        this.capacity = capacity;
        this.chunk = chunk;
        index = 0;
        array = new Object[this.capacity];
    }

    /**
     * Store object into MemoryConsumer.
     *
     * @param o - Object to store
     */
    private void store(Object o) {
        if (array == null) {
            throw new RuntimeException("Capacity should be set before storing");
        }
        array[index % capacity] = o;
        ++index;
    }

    public void consume(long memoryToFill) {
        long allocated = 0;
        while (allocated < memoryToFill) {
            store(new byte[chunk]);
            allocated += chunk;
        }
    }

    /**
     * Clear all stored objects.
     */
    public void clear() {
        array = null;
    }
}
