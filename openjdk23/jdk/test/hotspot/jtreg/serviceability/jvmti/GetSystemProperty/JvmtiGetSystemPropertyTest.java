/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8203329
 * @summary Verifies the JVMTI GetSystemProperty API returns the updated java.vm.info value
 * @requires vm.jvmti
 * @library /test/lib
 * @run main/othervm/native -agentlib:JvmtiGetSystemPropertyTest JvmtiGetSystemPropertyTest
 *
 */

public class JvmtiGetSystemPropertyTest {
    private static native String getSystemProperty();

    public static void main(String[] args) throws Exception {
        String vm_info = System.getProperty("java.vm.info");
        String vm_info_jvmti = getSystemProperty();
        System.out.println("java.vm.info from java:  " + vm_info);
        System.out.println("java.vm.info from jvmti: " + vm_info_jvmti);
        if (!vm_info.equals(vm_info_jvmti)) {
            throw new RuntimeException("java.vm.info poperties not equal: \"" +
                                       vm_info + "\" != \"" + vm_info_jvmti + "\"");
        }
    }
}
