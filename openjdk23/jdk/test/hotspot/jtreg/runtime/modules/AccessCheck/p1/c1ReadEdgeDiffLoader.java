/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package p1;

import myloaders.MyDiffClassLoader;
import p2.c2;

public class c1ReadEdgeDiffLoader {
    public c1ReadEdgeDiffLoader() {
        // The goal is to establish a read edge between module m1x
        // which is the module where p1.c1ReadEdgeDiffLoader is defined,
        // and the unnamed module that defines p2.c2.  This must be
        // done in 2 steps:
        //
        // Step #1: Establish a read edge between m1x, where c1ReadEdgeDiffLoader
        //          is defined, and the System ClassLoader's unnamed module,
        //          where MyDiffClassLoader is defined. This read edge
        //          is needed before we can obtain MyDiffClassLoader.loader2's unnamed module.
        //
        // Step #2: Establish a read edge between m1x, where c1ReadEdgeDiffLoader
        //          is defined, and the MyDiffClassLoader.loader2's unnamed module,
        //          where p2.c2 will be defined.

        // Step #1: read edge m1x -> System ClassLoader's unnamed module
        Module m1x = c1ReadEdgeDiffLoader.class.getModule();
        ClassLoader system_loader = ClassLoader.getSystemClassLoader();
        Module unnamed_module_one = system_loader.getUnnamedModule();
        m1x.addReads(unnamed_module_one);

        // Step #2: read edge m1x -> MyDiffClassLoader.loader2's unnamed module
        ClassLoader loader2 = MyDiffClassLoader.loader2;
        Module unnamed_module_two = loader2.getUnnamedModule();
        m1x.addReads(unnamed_module_two);

        // Attempt access - access should succeed since m1x can read
        //                  MyDiffClassLoader.loader2's unnamed module
        p2.c2 c2_obj = new p2.c2();
        c2_obj.method2();
    }
}
