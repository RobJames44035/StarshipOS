/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 6177732
 * @summary add hidden option to have compiler generate diagnostics in more machine-readable form
 * @compile/ref=Note.out -XDrawDiagnostics Note.java
 */
class Note
{
    static void useDeprecated() {
        String s = new String(new byte[3], 0);
    }
}
