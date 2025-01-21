/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8176147
 * @summary Throw ClassFormatError exception for multiple Signature attributes
 * @compile DupSignatureAttrs.jcod
 * @run main TestDupSignatureAttr
 */

public class TestDupSignatureAttr {
    public static void main(String args[]) throws Throwable {

        System.out.println("Regression test for bug 8176147");

        String[] badClasses = new String[] {
            "DupClassSigAttrs",
            "DupMthSigAttrs",
            "DupFldSigAttrs",
        };
        String[] messages = new String[] {
            "Multiple Signature attributes in class file",
            "Multiple Signature attributes for method",
            "Multiple Signature attributes for field",
        };

        for (int x = 0; x < badClasses.length; x++) {
            try {
                Class newClass = Class.forName(badClasses[x]);
                throw new RuntimeException("Expected ClassFormatError exception not thrown");
            } catch (java.lang.ClassFormatError e) {
                if (!e.getMessage().contains(messages[x])) {
                    throw new RuntimeException("Wrong ClassFormatError exception thrown: " +
                                               e.getMessage());
                }
            }
        }

        // Multiple Signature attributes but no duplicates.
        Class newClass = Class.forName("OkaySigAttrs");
    }
}
