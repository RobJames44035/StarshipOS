/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.obj;

import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.AllMemoryObject;

public class AllMemoryObjectProducer implements GarbageProducer<AllMemoryObject> {
        public AllMemoryObject create(long memory) {
                return new AllMemoryObject((int) memory);
        }

        public void validate(AllMemoryObject obj) {
        }
}
