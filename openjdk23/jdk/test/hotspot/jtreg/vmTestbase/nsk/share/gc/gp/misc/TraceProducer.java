/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.misc;

import nsk.share.gc.gp.DerivedProducer;
import nsk.share.gc.gp.GarbageProducer;

/*
 * GarbageProducer that traces the production.
 */
public class TraceProducer<T> extends DerivedProducer<T, T> {
        private String prid;

        public TraceProducer(GarbageProducer<T> parent) {
                super(parent);
                prid = "Create (" + parent + "): ";
        }

        public T create(long memory) {
                T obj = createParent(memory);
                System.out.println(prid + memory);
                return obj;
        }

        public void validate(T obj) {
                validateParent(obj);
        }
}
