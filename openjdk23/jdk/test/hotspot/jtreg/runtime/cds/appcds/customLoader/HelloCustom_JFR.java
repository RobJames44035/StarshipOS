/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary Same as HelloCustom, but add -XX:StartFlightRecording:dumponexit=true to the runtime
 *          options. This makes sure that the shared classes are compatible with both
 *          JFR and JVMTI ClassFileLoadHook.
 * @requires vm.hasJFR
 * @requires vm.cds
 * @requires vm.cds.custom.loaders
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds
 * @compile test-classes/HelloUnload.java test-classes/CustomLoadee.java
 * @build jdk.test.whitebox.WhiteBox jdk.test.lib.classloader.ClassUnloadCommon
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar hello.jar HelloUnload
 *                 jdk.test.lib.classloader.ClassUnloadCommon
 *                 jdk.test.lib.classloader.ClassUnloadCommon$1
 *                 jdk.test.lib.classloader.ClassUnloadCommon$TestFailure
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar hello_custom.jar CustomLoadee
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar WhiteBox.jar jdk.test.whitebox.WhiteBox
 * @run driver HelloCustom_JFR
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.whitebox.WhiteBox;

public class HelloCustom_JFR {
    public static void main(String[] args) throws Exception {
        HelloCustom.run("-XX:StartFlightRecording:dumponexit=true", "-Xlog:cds+jvmti=debug");
    }
}
