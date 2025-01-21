/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test Test6981737.java
 * @bug 6981737 8204565
 * @summary check for correct vm properties
 * @run main Test6981737
 * @author kamg
*/

public class Test6981737 {

    /**
     * Check the 'vendor' properties and java.vm.specification.version property.
     */
    public static void main(String[] args) throws Exception {

        String vendor_re = "Oracle Corporation";
        int feature_version = Runtime.version().feature();
        String vm_spec_version_re = Integer.toString(feature_version);

        verifyProperty("java.vm.specification.vendor", vendor_re);
        verifyProperty("java.specification.vendor", vendor_re);
        verifyProperty("java.vm.specification.version", vm_spec_version_re);
        System.out.println("PASS");
    }

    public static String verifyProperty(String name, String expected_re) {
        String value = System.getProperty(name, "");
        System.out.print("Checking " + name + ": \"" + value +
          "\".matches(\"" + expected_re + "\")... ");
        if (!value.matches(expected_re)) {
            System.out.println("no.");
            throw new RuntimeException("FAIL: Wrong value for " + name +
                " property, \"" + value + "\", expected to be of form: \"" +
                expected_re + "\"");
        }
        System.out.println("yes.");
        return value;
    }
}
