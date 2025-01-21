/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package p1;

import p2.c2;

public class c1Loose {
    public c1Loose() {
        // Attempt access - access should succeed since m1x is a loose module
        p2.c2 c2_obj = new p2.c2();
        c2_obj.method2();
    }
}
