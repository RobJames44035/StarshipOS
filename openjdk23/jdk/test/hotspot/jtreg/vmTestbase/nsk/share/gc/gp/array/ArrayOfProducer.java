/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.array;

import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.gp.DerivedProducer;
import nsk.share.gc.gp.GarbageUtils;
import nsk.share.gc.gp.MemoryStrategy;
import nsk.share.gc.Memory;
import nsk.share.TestFailure;

/**
 * GarbageProducer implementation that produces arrays of objects
 * determined by parent garbage producer. A memory strategy is
 * used to determine how memory is distributed between array size
 * and size of each object in array.
 */
public class ArrayOfProducer<T> extends DerivedProducer<Object[], T> {
        private int n;

        public ArrayOfProducer(GarbageProducer<T> parent, int n) {
                super(parent);
                this.n = n;
        }

        public ArrayOfProducer(GarbageProducer<T> parent) {
                this(parent, 2);
        }

        public Object[] create(long memory) {
                Object[] array = new Object[n];
                long objectSize = (memory - Memory.getArrayExtraSize() - Memory.getReferenceSize() * n) / n;
                for (int i = 0; i < n; ++i)
                        array[i] = createParent(objectSize);
                return array;
        }

        public void validate(Object[] obj) {
                for (int i = 0; i < obj.length; ++i)
                        validateParent((T) obj[i]);
        }
}
