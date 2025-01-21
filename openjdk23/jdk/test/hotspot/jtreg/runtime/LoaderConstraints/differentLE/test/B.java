/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package test;

// This class is loaded via Loader2. Using D_ambgs here will trigger
// loading it's second version with Loader2.
public class B implements A {
    public D_ambgs[] gen() {
        D_ambgs[] x = new D_ambgs[1];
        x[0] = new D_ambgs();
        return x;
    }
}
