/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package p1;
public class T4720356a {
    void m() {}
}
class T4720356c extends p2.T4720356b {
    // conflicting return type, even though a.m() not inherited
    public int m() { return 1; }
}
