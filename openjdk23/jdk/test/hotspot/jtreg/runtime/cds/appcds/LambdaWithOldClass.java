/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8259275
 * @summary VM should not crash during CDS dump and run time when a lambda
 *          expression references an old version of class.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/OldClass.jasm
 * @compile test-classes/LambdaWithOldClassApp.java
 * @run driver LambdaWithOldClass
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class LambdaWithOldClass {

    public static void main(String[] args) throws Exception {
        String mainClass = "LambdaWithOldClassApp";
        String namePrefix = "lambdawitholdclass";
        JarBuilder.build(namePrefix, mainClass, "OldClass", "TestInterface");

        String appJar = TestCommon.getTestJar(namePrefix + ".jar");
        String classList = namePrefix + ".list";
        String archiveName = namePrefix + ".jsa";

        // dump class list
        CDSTestUtils.dumpClassList(classList, "-cp", appJar, mainClass);

        // create archive with the class list
        CDSOptions opts = (new CDSOptions())
            .addPrefix("-XX:ExtraSharedClassListFile=" + classList,
                       "-cp", appJar,
                       "-Xlog:class+load,cds")
            .setArchiveName(archiveName);
        CDSTestUtils.createArchiveAndCheck(opts);

        // run with archive
        CDSOptions runOpts = (new CDSOptions())
            .addPrefix("-cp", appJar, "-Xlog:class+load,cds=debug")
            .setArchiveName(archiveName)
            .setUseVersion(false)
            .addSuffix(mainClass);
        OutputAnalyzer output = CDSTestUtils.runWithArchive(runOpts);
        output.shouldContain("[class,load] LambdaWithOldClassApp source: shared objects file")
              .shouldHaveExitValue(0);
        if (!CDSTestUtils.isAOTClassLinkingEnabled()) {
            // With AOTClassLinking, we don't archive any lambda with old classes in the method
            // signatures.
            output.shouldMatch(".class.load. LambdaWithOldClassApp[$][$]Lambda.*/0x.*source:.*shared objects file");
        }
    }
}
