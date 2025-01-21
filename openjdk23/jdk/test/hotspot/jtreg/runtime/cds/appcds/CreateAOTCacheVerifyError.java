/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8346457
 * @summary VM should not crash during AOT cache creation when encountering a
 *          class with VerifyError.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 * @compile test-classes/BadLookupSwitch.jcod
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar badlookupswitch.jar BadLookupSwitch
 * @run driver CreateAOTCacheVerifyError
 */

import jdk.test.lib.helpers.ClassFileInstaller;
import jdk.test.lib.process.OutputAnalyzer;

public class CreateAOTCacheVerifyError {

    public static void main(String[] args) throws Exception {
        String appJar = ClassFileInstaller.getJarPath("badlookupswitch.jar");
        String classList[] = { BadLookupSwitch.class.getName() };
        OutputAnalyzer out = TestCommon.testDump(appJar, classList);
        out.shouldContain("Preload Warning: Verification failed for BadLookupSwitch");
        out.shouldHaveExitValue(0);
    }
}
