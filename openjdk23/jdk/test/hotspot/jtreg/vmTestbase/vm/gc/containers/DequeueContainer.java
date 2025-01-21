/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
package vm.gc.containers;

import java.util.Deque;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.gp.MemoryStrategy;

/*
 * The dequeue container update remove a first or last
 * elements from queue and add the same number of elements.
 *
 */
class DequeueContainer extends TypicalContainer {

    Deque queue;
    boolean isLIFO;
    int threadsCount;

    public DequeueContainer(Deque queue, long maximumSize,
            GarbageProducer garbageProducer, MemoryStrategy memoryStrategy, Speed speed,
            int threadsCount) {
        super(maximumSize, garbageProducer, memoryStrategy, speed);
        this.threadsCount = threadsCount;
        this.queue = queue;
    }

    public void initialize() {
        for (int i = 0; i < count; i++) {
            if (!stresser.continueExecution()) {
                return;
            }
            queue.add(garbageProducer.create(size));
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < count * speed.getValue() / (threadsCount * 100); i++) {
            if (!stresser.continueExecution()) {
                return;
            }
            Object obj = null;
            if (isLIFO) {
                obj = queue.removeFirst();
            } else {
                obj = queue.removeLast();
            }
            garbageProducer.validate(obj);
        }
        for (int i = 0; i < count * speed.getValue() / (threadsCount * 100); i++) {
            if (!stresser.continueExecution()) {
                return;
            }
            queue.addFirst(garbageProducer.create(size));
        }
        isLIFO = !isLIFO;
    }
}
