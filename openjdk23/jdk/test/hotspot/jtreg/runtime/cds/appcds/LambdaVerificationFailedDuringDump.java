/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Dumping of lambda proxy classes should not crash VM in case the caller class has failed verification.
 * @requires vm.cds
 * @library /test/lib
 * @compile test-classes/BadInvokeDynamic.jcod
 * @run driver LambdaVerificationFailedDuringDump
 */

import jdk.test.lib.process.OutputAnalyzer;

public class LambdaVerificationFailedDuringDump {

    public static void main(String[] args) throws Exception {
        JarBuilder.build("badinvokedynamic", "BadInvokeDynamic");

        String appJar = TestCommon.getTestJar("badinvokedynamic.jar");

        OutputAnalyzer out = TestCommon.dump(appJar,
        TestCommon.list("BadInvokeDynamic",
                        "@lambda-proxy BadInvokeDynamic run ()Ljava/lang/Runnable; ()V REF_invokeStatic BadInvokeDynamic lambda$doTest$0 ()V ()V"));
        out.shouldContain("Preload Warning: Verification failed for BadInvokeDynamic")
           .shouldContain("Skipping BadInvokeDynamic: Failed verification")
           .shouldHaveExitValue(0);
    }
}
