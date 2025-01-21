/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package p;
import p.ImportInaccessible.Nested.*;

class ImportInaccessible {
    static class Nested<X extends Inner> {
        private static class Inner{}
     }
}
