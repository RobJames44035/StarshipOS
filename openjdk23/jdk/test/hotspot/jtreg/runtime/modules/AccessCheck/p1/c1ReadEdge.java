/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package p1;

import p2.c2;

public class c1ReadEdge {
    public c1ReadEdge() {
        // Establish read edge from module m1x, where c1ReadEdge is defined,
        // to the unnamed module, where p2.c2 will be defined.
        Module m1x = c1ReadEdge.class.getModule();
        ClassLoader loader = c1ReadEdge.class.getClassLoader();
        Module unnamed_module = loader.getUnnamedModule();
        m1x.addReads(unnamed_module);

        // Attempt access - access should succeed
        p2.c2 c2_obj = new p2.c2();
        c2_obj.method2();
    }
}
