/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package test;

public class IAE78_B {

    public static void create() {
        new IAE78_A();
    }

    public static void access() {
        IAE78_A.f.hashCode();
    }
}
