/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package utils;

public class Utils {

    public static void log(String field, String val1, String val2) {
        System.out.println(field + " mismatch. " + val1 + " vs " + val2);
    }

    public static boolean compareStrings(String s1, String s2) {

        if (s1 != null && s1.equals(Consts.UNKNOWN)
                || s2 != null && s2.equals(Consts.UNKNOWN)) {
            return true;
        }

        if (s1 == null && s2 != null || s1 != null && s2 == null) {
            return false;
        }

        if (s1 == null || s2 == null) {
            return true;
        }
        return s1.equals(s2);
    }

    public static void sleep() {
        try {
            while (true) {
                Thread.sleep(Long.MAX_VALUE);
            }
        } catch (InterruptedException e) {
        }
    }
}
