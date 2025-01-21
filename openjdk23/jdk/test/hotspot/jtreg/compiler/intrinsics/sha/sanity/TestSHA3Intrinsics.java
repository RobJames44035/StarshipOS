/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8252204
 * @summary Verify that SHA3-224, SHA3-256, SHA3-384, SHA3-512 intrinsic is actually used.
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
 *                   -XX:+LogCompilation -XX:LogFile=positive_224.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:+UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-224
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_224.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:-UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-224
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_256.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:+UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-256
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_256.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:-UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-256
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_384.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:+UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-384
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_384.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:-UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-384
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_512.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:+UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-512
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_512.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA3::*
 *                   -XX:-UseSHA3Intrinsics
 *                   -Dalgorithm=SHA3-512
 *                   compiler.intrinsics.sha.sanity.TestSHA3Intrinsics
 * @run main/othervm -DverificationStrategy=VERIFY_INTRINSIC_USAGE
 *                    compiler.testlibrary.intrinsics.Verifier positive_224.log positive_256.log positive_384.log positive_512.log
 *                    negative_224.log negative_256.log negative_384.log negative_512.log
 */

package compiler.intrinsics.sha.sanity;

import compiler.testlibrary.sha.predicate.IntrinsicPredicates;

public class TestSHA3Intrinsics {
    public static void main(String args[]) throws Exception {
        new DigestSanityTestBase(IntrinsicPredicates.isSHA3IntrinsicAvailable(),
                DigestSanityTestBase.SHA3_INTRINSIC_ID).test();
    }
}
