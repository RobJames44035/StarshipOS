/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package pkg1;

import java.io.*;

/**
 * A test class where outer class is package private and the inner class is
 * protected.
 */

class ProtectedInnerClass {

    protected static class ProInnerClass implements java.io.Serializable {

        public final int SERIALIZABLE_CONSTANT1 = 1;

        /**
         * @param s ObjectInputStream.
         * @throws IOException when there is an I/O error.
         * @serial
         */
        private void readObject(ObjectInputStream s) throws IOException {
        }

        /**
         * @param s ObjectOutputStream.
         * @throws IOException when there is an I/O error.
         * @serial
         */
        private void writeObject(ObjectOutputStream s) throws IOException {
        }
    }
}
