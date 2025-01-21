/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package pkg1;

import java.io.*;

/**
 * A test class where the outer class is package private and the inner class is private
 * and a nested inner class is protected.
 */

class NestedInnerClass {

    private static class InnerClass {

        protected static class ProNestedInnerClass implements java.io.Serializable {

            public final int SERIALIZABLE_CONSTANT = 1;

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
}
