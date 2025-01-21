/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

interface Compar<T> {
    int compareTo(T o);
}
abstract class ErasureClashCrash implements Compar<ErasureClashCrash> {
    public int compareTo(Object o) {
        return 1;
    }
}
