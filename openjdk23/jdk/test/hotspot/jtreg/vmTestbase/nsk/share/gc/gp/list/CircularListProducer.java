/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.list;

import nsk.share.gc.LinkedMemoryObject;
import nsk.share.gc.Memory;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.gp.MemoryStrategy;

/**
 * Garbage producer that produces circular linked lists.
 */
public class CircularListProducer implements GarbageProducer<LinkedMemoryObject> {
        private MemoryStrategy memoryStrategy;

        public CircularListProducer(MemoryStrategy memoryStrategy) {
                this.memoryStrategy = memoryStrategy;
        }

        public LinkedMemoryObject create(long memory) {
                long objectSize = memoryStrategy.getSize(memory);
                int objectCount = memoryStrategy.getCount(memory);
                return Memory.makeCircularList(objectCount, (int) objectSize);
        }

        public void validate(LinkedMemoryObject obj) {
                LinkedMemoryObject o = obj;
                while (o != null && o != obj)
                        o = o.getNext();
        }
}
