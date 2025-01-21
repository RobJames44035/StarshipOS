/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8186211
 * @summary CONSTANT_Dynamic_info structure present with various bad BSM index, BSM array attribute checks.
 * @compile CondyBadBSMIndex.jcod
 * @compile CondyEmptyBSMArray1.jcod
 * @compile CondyNoBSMArray.jcod
 * @run main/othervm -Xverify:all CondyBadBSMArrayTest
 */

// Test that a CONSTANT_Dynamic_info structure present with the following issues:
// 1. The CONSTANT_Dynamic_info structure's bootstrap_method_attr_index value is
//    an index outside of the array size.
// 2. An empty BootstrapMethods Attribute array
// 3. No BootstrapMethods Attribute array present.
public class CondyBadBSMArrayTest {
    public static void main(String args[]) throws Throwable {
        // 1. The CONSTANT_Dynamic_info structure's bootstrap_method_attr_index is outside the array size
        try {
            Class newClass = Class.forName("CondyBadBSMIndex");
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        } catch (java.lang.ClassFormatError e) {
            if (!e.getMessage().contains("Short length on BootstrapMethods in class file")) {
                throw new RuntimeException("ClassFormatError thrown, incorrect message");
            }
            System.out.println("Test CondyBadBSMIndex passed: " + e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        }

        // 2. An empty BootstrapMethods Attribute array - contains zero elements
        try {
            Class newClass = Class.forName("CondyEmptyBSMArray1");
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        } catch (java.lang.ClassFormatError e) {
            if (!e.getMessage().contains("Short length on BootstrapMethods in class file")) {
                throw new RuntimeException("ClassFormatError thrown, incorrect message");
            }
            System.out.println("Test CondyEmptyBSMArray1 passed: " + e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        }

        // 3. No BootstrapMethods Attribute array present`
        try {
            Class newClass = Class.forName("CondyNoBSMArray");
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        } catch (java.lang.ClassFormatError e) {
            if (!e.getMessage().contains("Missing BootstrapMethods attribute in class file")) {
                throw new RuntimeException("ClassFormatError thrown, incorrect message");
            }
            System.out.println("Test CondyNoBSMArray passed: " + e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        }
    }
}
