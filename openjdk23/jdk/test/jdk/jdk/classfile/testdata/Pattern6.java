/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package testdata;

public class Pattern6 {

        static void troublesCausingMethod() {
            boolean b = true;
            String s1 = null;
            String s2;
            if (s1 != null) {
                s2 = s1;
            } else {
                try {
                    s2 = null;
                    if (b) {}
                    s2.equals(null);
                } catch (Error ex) {
                    throw ex;
                }
            }
            if (null == s2) {}
        }
}
