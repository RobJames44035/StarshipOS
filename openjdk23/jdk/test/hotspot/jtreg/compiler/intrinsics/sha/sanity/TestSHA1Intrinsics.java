/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify that SHA-1 intrinsic is actually used.
 *
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA::*
 *                   -XX:+UseSHA1Intrinsics
 *                   -Dalgorithm=SHA-1
 *                   compiler.intrinsics.sha.sanity.TestSHA1Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA::*
 *                   -XX:-UseSHA1Intrinsics
 *                   -Dalgorithm=SHA-1
 *                   compiler.intrinsics.sha.sanity.TestSHA1Intrinsics
 * @run main/othervm -DverificationStrategy=VERIFY_INTRINSIC_USAGE
 *                   compiler.testlibrary.intrinsics.Verifier positive.log negative.log
 */

package compiler.intrinsics.sha.sanity;

import compiler.testlibrary.sha.predicate.IntrinsicPredicates;

public class TestSHA1Intrinsics {
    public static void main(String args[]) throws Exception {
        new DigestSanityTestBase(IntrinsicPredicates.isSHA1IntrinsicAvailable(),
                DigestSanityTestBase.SHA1_INTRINSIC_ID).test();
    }
}
