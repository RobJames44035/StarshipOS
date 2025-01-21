/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package p1;
public class T4720359a {
    static void m() {}
}
class T4720359c extends p2.T4720359b {
    // conflicting return type, even though a.m() not inherited
    public static int m() { return 1; }
}
