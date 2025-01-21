/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share.pool.subpack;

import compiler.compilercontrol.share.pool.MethodHolder;
import compiler.compilercontrol.share.pool.SubMethodHolder;

/**
 * This is a clone of the c.c.s.pool.sub.Klass used to test pattern matching
 * Full class name contains both suffix (Dup) and prefix (c.c.s.pool.subpack)
 */
public class KlassDup extends MethodHolder {
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

    // Internal class and constructor
    public static class Internal extends SubMethodHolder {
        public Internal() { }

        public Double method(Float fl) { return Double.valueOf(fl); }

        public Double methodDup() {
            return Math.exp(1.0);
        }

        public static Integer smethod(Integer arg) {
            Integer var = 1024;
            return arg + var;
        }
    }
}
