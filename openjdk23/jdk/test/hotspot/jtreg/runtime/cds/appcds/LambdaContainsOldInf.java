/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8274944 8276184
 * @summary VM should not crash during CDS dump when a lambda proxy class
 *          contains an old version of interface.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/OldProvider.jasm
 * @compile test-classes/LambdaContainsOldInfApp.java
 * @run driver LambdaContainsOldInf
 */

import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.OutputAnalyzer;

public class LambdaContainsOldInf {

    public static void main(String[] args) throws Exception {
        String mainClass = "LambdaContainsOldInfApp";
        String namePrefix = "lambdacontainsoldinf";
        JarBuilder.build(namePrefix, mainClass, "OldProvider");

        String appJar = TestCommon.getTestJar(namePrefix + ".jar");
        String classList = namePrefix + ".list";
        String archiveName = namePrefix + ".jsa";

        String[] mainArgs = { "dummy", "addLambda" };

        for (String mainArg : mainArgs) {
            // dump class list
            CDSTestUtils.dumpClassList(classList, "-cp", appJar, mainClass, mainArg);

            // create archive with the class list
            CDSOptions opts = (new CDSOptions())
                .addPrefix("-XX:ExtraSharedClassListFile=" + classList,
                           "-cp", appJar,
                           "-Xlog:class+load,cds")
                .setArchiveName(archiveName);
            OutputAnalyzer output = CDSTestUtils.createArchiveAndCheck(opts);
            TestCommon.checkExecReturn(output, 0, true,
                                       "Skipping OldProvider: Old class has been linked");
            if (CDSTestUtils.isAOTClassLinkingEnabled()) {
                output.shouldMatch("Cannot aot-resolve Lambda proxy because OldProvider is excluded");
            } else {
                output.shouldMatch("Skipping.LambdaContainsOldInfApp[$][$]Lambda.*0x.*:.*Old.class.has.been.linked");
            }

            // run with archive
            CDSOptions runOpts = (new CDSOptions())
                .addPrefix("-cp", appJar, "-Xlog:class+load,cds=debug")
                .setArchiveName(archiveName)
                .setUseVersion(false)
                .addSuffix(mainClass)
                .addSuffix(mainArg);
            output = CDSTestUtils.runWithArchive(runOpts);
            TestCommon.checkExecReturn(output, 0, true,
                "[class,load] LambdaContainsOldInfApp source: shared objects file");
            output.shouldMatch(".class.load. OldProvider.source:.*lambdacontainsoldinf.jar")
                  .shouldMatch(".class.load. LambdaContainsOldInfApp[$][$]Lambda.*/0x.*source:.*LambdaContainsOldInf");
       }
    }
}
