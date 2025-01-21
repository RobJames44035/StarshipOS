/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4406917
 * @summary static call instead of virtual call in inner classes (access method bug)
 * @author gafter
 *
 * @compile a/A.java b/B.java c/C.java Main.java
 * @run main Main
 */


import c.*;

public class Main {
    static public void main(String args[]) {
        C c = new C();
        c.precall();
    }
}
