/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4936182
 * @summary local variable annotations
 * @author gafter
 *
 * @compile Local.java
 */

class Local {
    {
        @Deprecated int x;
    }
}
