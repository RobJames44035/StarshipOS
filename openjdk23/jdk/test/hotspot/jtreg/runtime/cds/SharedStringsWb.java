/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

// This class is used by the test SharedStrings.java
// It should be launched in CDS mode
public class SharedStringsWb {
    public static void main(String[] args) throws Exception {
        WhiteBox wb = WhiteBox.getWhiteBox();

        if (!wb.areSharedStringsMapped()) {
            System.out.println("Shared strings are not mapped, assuming PASS");
            return;
        }

        // The string below is known to be added to CDS archive
        String s = "<init>";
        String internedS = s.intern();

        // Check that it's a valid string
        if (s.getClass() != String.class || !(s instanceof String)) {
            throw new RuntimeException("Shared string is not a valid String: FAIL");
        }

        if (wb.isSharedInternedString(internedS)) {
            System.out.println("Found shared string, result: PASS");
        } else {
            throw new RuntimeException("String is not shared, result: FAIL");
        }
    }
}


