/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify that SHA-512 multi block intrinsic is actually used.
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
 *                   -XX:+LogCompilation -XX:LogFile=positive_384.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA5::*
 *                   -XX:+UseSHA512Intrinsics -XX:-UseMD5Intrinsics
 *                   -XX:-UseSHA1Intrinsics -XX:-UseSHA256Intrinsics
 *                   -Dalgorithm=SHA-384
 *                   compiler.intrinsics.sha.sanity.TestSHA512MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_384_def.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA5::*
 *                   -XX:+UseSHA512Intrinsics -Dalgorithm=SHA-384
 *                   compiler.intrinsics.sha.sanity.TestSHA512MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_384.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA5::* -XX:-UseSHA
 *                   -Dalgorithm=SHA-384
 *                   compiler.intrinsics.sha.sanity.TestSHA1Intrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_512.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA5::*
 *                   -XX:+UseSHA512Intrinsics -XX:-UseMD5Intrinsics
 *                   -XX:-UseSHA1Intrinsics -XX:-UseSHA256Intrinsics
 *                   -Dalgorithm=SHA-512
 *                   compiler.intrinsics.sha.sanity.TestSHA512MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_512_def.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA5::*
 *                   -XX:+UseSHA512Intrinsics -Dalgorithm=SHA-512
 *                   compiler.intrinsics.sha.sanity.TestSHA512MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_512.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA5::* -XX:-UseSHA
 *                   -Dalgorithm=SHA-512
 *                   compiler.intrinsics.sha.sanity.TestSHA512MultiBlockIntrinsics
 * @run main/othervm -DverificationStrategy=VERIFY_INTRINSIC_USAGE
 *                    compiler.testlibrary.intrinsics.Verifier positive_384.log positive_512.log
 *                    positive_384_def.log positive_512_def.log negative_384.log
 *                    negative_512.log
 */

package compiler.intrinsics.sha.sanity;

import compiler.testlibrary.sha.predicate.IntrinsicPredicates;

public class TestSHA512MultiBlockIntrinsics {
    public static void main(String args[]) throws Exception {
        new DigestSanityTestBase(IntrinsicPredicates.isSHA512IntrinsicAvailable(),
                DigestSanityTestBase.MB_INTRINSIC_ID).test();
    }
}
