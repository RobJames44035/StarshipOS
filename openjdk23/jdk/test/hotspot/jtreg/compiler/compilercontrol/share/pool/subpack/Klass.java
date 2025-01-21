/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share.pool.subpack;

import compiler.compilercontrol.share.pool.MethodHolder;

/**
 * Simple class with methods to test signatures
 * This is a clone of the c.c.s.pool.sub.Klass, but without inner class
 * This class has different package name to test prefix patterns like *Klass.
 * *Klass patern should match both c.c.s.pool.sub.Klass and c.c.s.pool.subpack.Klass
 */
public class Klass extends MethodHolder {
    public void method(int a, String[] ss, Integer i, byte[] bb, double[][] dd) { }

    public void method() { }

    public static String smethod() {
        return "ABC";
    }

    public static String smethod(int iarg, int[] aarg) {
        return "ABC";
    }

    public static Integer smethod(Integer arg) {
        Integer var = 1024;
        return arg + var;
    }
}
