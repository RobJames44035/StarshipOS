/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8225093
 * @summary Check that JVMTI GetSystemProperty API returns the right values for
 *          property jdk.boot.class.path.append.
 * @requires vm.jvmti
 * @library /test/lib
 * @run main/othervm/native -agentlib:GetBootClassPathAppendProp GetBootClassPathAppendProp
 * @run main/othervm/native -Xbootclasspath/a:blah -agentlib:GetBootClassPathAppendProp GetBootClassPathAppendProp one_arg
 *
 */

public class GetBootClassPathAppendProp {
    private static native String getSystemProperty();

    public static void main(String[] args) throws Exception {
        String path = getSystemProperty();
        if (args.length > 0) {
            if (!path.equals("blah")) {
                throw new RuntimeException("Wrong value returned for jdk.boot.class.path.append: " +
                                           path);
           }
        } else {
            if (path != null) {
                throw new RuntimeException("Null value expected for jdk.boot.class.path.append: " +
                                           path);
            }
        }
    }
}
