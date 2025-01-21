/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp;

/**
 * Interface that defines a way to produce garbage.
 */
public interface GarbageProducer<T> {
        /**
         * Produce garbage of given size.
         *
         * @param memory size in bytes
         * @return produced garbage
         */
        public T create(long memory);

        /**
         * Validate earlier produced object.
         *
         * @param obj earlier produced garbage
         * @throws TestFailure if validation fails
         */
        public void validate(T obj);
}
