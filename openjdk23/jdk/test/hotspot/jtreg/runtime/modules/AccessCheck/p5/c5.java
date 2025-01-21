/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package p5;

import java.lang.Module;
import p2.c2;

public class c5 {
    public void method5(p2.c2 param) {
        // The invokedynamic opcode that gets generated for the '+' string
        // concatenation operator throws an IllegalAccessError when trying to
        // access 'param'.
        System.out.println("In c5's method5 with param = " + param);
    }

    public void methodAddReadEdge(Module m) {
        // Add a read edge from p5/c5's module (first_mod) to second_mod
        c5.class.getModule().addReads(m);
    }
}
