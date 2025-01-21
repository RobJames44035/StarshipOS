/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
package vm.gc.containers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.gp.MemoryStrategy;
import nsk.share.test.LocalRandom;

/*
 * The MapContainer select a certain amout of random elements and remove
 * them. After this it put the same number of elements with random keys.
 */
class MapContainer extends TypicalContainer {

    Map map;

    public MapContainer(Map map, long maximumSize, GarbageProducer garbageProducer,
            MemoryStrategy memoryStrategy, Speed speed) {
        super(maximumSize, garbageProducer, memoryStrategy, speed);
        this.map = map;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < count; i++) {
            if (!stresser.continueExecution()) {
                return;
            }
            map.put(i, garbageProducer.create(size));
        }
    }

    @Override
    public void update() {
        Set<Integer> updated = new HashSet();
        for (int i = 0; i < count * speed.getValue() / 100; i++) {
            updated.add(LocalRandom.nextInt((int) count));
        }
        for (Integer i : updated) {
            if (!stresser.continueExecution()) {
                return;
            }
            Object obj = map.remove(i);
            if (obj != null) {
                garbageProducer.validate(obj);
            }
        }
        for (Integer i : updated) {
            if (!stresser.continueExecution()) {
                return;
            }
            map.put(i, garbageProducer.create(size));
        }
    }
}
