/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
package vm.gc.containers;

import nsk.share.gc.Memory;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.gp.MemoryStrategy;
import nsk.share.test.ExecutionController;

public interface Container {
    public void setExecutionController(ExecutionController stresser);
    public void initialize();
    public void update();
}

abstract class TypicalContainer implements Container {

    protected ExecutionController stresser;
    protected long count;
    protected long size;
    protected GarbageProducer garbageProducer;
    protected Speed speed;

    public TypicalContainer(long maximumSize, GarbageProducer garbageProducer,
            MemoryStrategy memoryStrategy, Speed speed) {
        this.count = memoryStrategy.getCount(maximumSize);
        this.size = memoryStrategy.getSize(maximumSize);
        // typical container have at least reference to other element
        // and to data which is really size of "size" really for 100 bytes
        // overhead is about 50%
        final int structureOverHead = 6 * Memory.getReferenceSize();
        if (this.size < structureOverHead * 100) {
            this.count = this.count * (this.size - structureOverHead) / this.size;
        }
        this.garbageProducer = garbageProducer;
        this.speed = speed;
        System.err.format("Creating container: size = %s count = %s\n", size, count);
    }

    public void setExecutionController(ExecutionController stresser) {
        this.stresser = stresser;
    }


}
