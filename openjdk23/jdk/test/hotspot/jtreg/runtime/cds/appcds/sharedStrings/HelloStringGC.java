/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

public class HelloStringGC {
    public static String[] array01 = new String[1000];
    public static String[] array02 = new String[1000];

    public static void main(String args[]) throws RuntimeException {
        String testString1 = "shared_test_string_unique_14325";
        String testString2 = "test123";

        WhiteBox wb = WhiteBox.getWhiteBox();
        if (wb.areSharedStringsMapped() && !wb.isSharedInternedString(testString1)) {
            throw new RuntimeException("testString1 is not shared");
        }

        for (int i=0; i<5; i++) {
            allocSomeStrings(testString1, testString2);
            array01 = null;
            array02 = null;
            System.gc();
            sleep(300);
            array01 = new String[1000];
            array02 = new String[1000];
        }

        wb.fullGC();

        System.out.println("HelloStringGC: PASS");
    }

    private static void allocSomeStrings(String s1, String s2) {
        for (int i = 0; i < 1000; i ++) {
            array01[i] = new String(s1);
            array02[i] = new String(s2);
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

}
