/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package pkg3;

public interface I {

    int hashCode();

    boolean equals(Object obj);

    String toString();

    // No matter what your IDE might show you, from JLS 9.6.4.4 it follows that
    // the "clone" (as well as currently deprecated "finalize") method cannot
    // be overridden by an interface method in the same way "hashCode", "equals"
    // and "toString" can. This is because "clone" is not public.
    Object clone();
}
