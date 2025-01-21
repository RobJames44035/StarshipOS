/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

public class GCSharedStringsDuringDumpWb {
    public static void main(String[] args) throws Exception {
        WhiteBox wb = WhiteBox.getWhiteBox();
        String s = "shared_test_string_unique_14325";
        s = s.intern();
        CheckString(wb, s);
        for (int i=0; i<100000; i++) {
            s = "generated_string " + i;
            s = s.intern();
            CheckString(wb, s);
        }
    }

    public static void CheckString(WhiteBox wb, String s) {
        if (wb.areSharedStringsMapped() && !wb.isSharedInternedString(s)) {
            throw new RuntimeException("String is not shared.");
        }
    }
}
