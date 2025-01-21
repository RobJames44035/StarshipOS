/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify that SHA-256 multi block intrinsic is actually used.
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
 *                   -XX:CompileOnly=sun.security.provider.SHA2::*
 *                   -XX:+UseSHA256Intrinsics -XX:-UseMD5Intrinsics
 *                   -XX:-UseSHA1Intrinsics -XX:-UseSHA512Intrinsics
 *                   -Dalgorithm=SHA-224
 *                   compiler.intrinsics.sha.sanity.TestSHA256MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_224_def.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA2::*
 *                   -XX:+UseSHA256Intrinsics -Dalgorithm=SHA-224
 *                   compiler.intrinsics.sha.sanity.TestSHA256MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_224.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA2::* -XX:-UseSHA
 *                   -Dalgorithm=SHA-224
 *                   compiler.intrinsics.sha.sanity.TestSHA256MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_256.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA2::*
 *                   -XX:+UseSHA256Intrinsics -XX:-UseMD5Intrinsics
 *                   -XX:-UseSHA1Intrinsics -XX:-UseSHA512Intrinsics
 *                   -Dalgorithm=SHA-256
 *                   compiler.intrinsics.sha.sanity.TestSHA256MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_256_def.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA::*
 *                   -XX:+UseSHA256Intrinsics -Dalgorithm=SHA-256
 *                   compiler.intrinsics.sha.sanity.TestSHA256MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative_256.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.SHA::* -XX:-UseSHA
 *                   -Dalgorithm=SHA-256
 *                   compiler.intrinsics.sha.sanity.TestSHA256MultiBlockIntrinsics
 * @run main/othervm -DverificationStrategy=VERIFY_INTRINSIC_USAGE
 *                   compiler.testlibrary.intrinsics.Verifier positive_224.log positive_256.log
 *                   positive_224_def.log positive_256_def.log negative_224.log
 *                   negative_256.log
 */

package compiler.intrinsics.sha.sanity;
import compiler.testlibrary.sha.predicate.IntrinsicPredicates;

public class TestSHA256MultiBlockIntrinsics {
    public static void main(String args[]) throws Exception {
        new DigestSanityTestBase(IntrinsicPredicates.isSHA256IntrinsicAvailable(),
                DigestSanityTestBase.MB_INTRINSIC_ID).test();
    }
}
