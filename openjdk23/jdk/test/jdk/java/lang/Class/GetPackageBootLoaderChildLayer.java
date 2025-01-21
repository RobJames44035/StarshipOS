/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @requires !vm.graal.enabled
 * @modules jdk.attach
 * @run main/othervm --limit-modules jdk.attach -Djdk.attach.allowAttachSelf
 *    GetPackageBootLoaderChildLayer
 * @summary Exercise Class.getPackage on a class defined to the boot loader
 *    but in a module that is in a child layer rather than the boot layer
 */

import com.sun.tools.attach.VirtualMachine;

public class GetPackageBootLoaderChildLayer {
    public static void main(String[] args) throws Exception {
        // ensure that the java.management module is not in the boot layer
        ModuleLayer.boot().findModule("java.management").ifPresent(m -> {
            throw new RuntimeException("java.management loaded!!!");
        });

        // start local JMX agent via the attach mechanism
        String vmid = "" + ProcessHandle.current().pid();
        VirtualMachine vm = VirtualMachine.attach(vmid);
        vm.startLocalManagementAgent();

        // check layer, class loader, and Package object
        Class<?> clazz = Class.forName("javax.management.MXBean");
        if (clazz.getModule().getLayer() == ModuleLayer.boot())
            throw new RuntimeException("Module is in boot layer!!!");
        ClassLoader loader = clazz.getClassLoader();
        if (loader != null)
            throw new RuntimeException("Unexpected class loader: " + loader);
        Package p = clazz.getPackage();
        if (!p.getName().equals("javax.management"))
            throw new RuntimeException("Unexpected package " + p);
    }
}
