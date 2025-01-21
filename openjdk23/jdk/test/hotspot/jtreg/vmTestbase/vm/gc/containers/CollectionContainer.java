/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
package vm.gc.containers;

import java.util.Collection;
import java.util.Iterator;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.gp.MemoryStrategy;


/*
 * This class updates collection by removal of random elements
 * via iterator and adding the same number of elements.
 */
class CollectionContainer extends TypicalContainer {

    Collection collection;

    public CollectionContainer(Collection collection, long maximumSize,
            GarbageProducer garbageProducer, MemoryStrategy memoryStrategy, Speed speed) {
        super(maximumSize, garbageProducer, memoryStrategy, speed);
        this.collection = collection;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < count; i++) {
            if (!stresser.continueExecution()) {
                return;
            }
            collection.add(garbageProducer.create(size));
        }
    }

    @Override
    public void update() {
        Iterator iter = collection.iterator();
        int i = 0;
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (i++ / 100 < speed.getValue()) {
                if (!stresser.continueExecution()) {
                    return;
                }
                iter.remove();
                garbageProducer.validate(obj);
            }
        }
        while (i != 0) {
            if (i-- / 100 < speed.getValue()) {
                if (!stresser.continueExecution()) {
                    return;
                }
                collection.add(garbageProducer.create(size));
            }
        }
    }
}
