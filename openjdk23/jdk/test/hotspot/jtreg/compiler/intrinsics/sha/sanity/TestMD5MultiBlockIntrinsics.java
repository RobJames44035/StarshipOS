/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8035968
 * @summary Verify that MD5 multi block intrinsic is actually used.
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
 *                   -XX:CompileOnly=sun.security.provider.MD5::*
 *                   -XX:+UseMD5Intrinsics -XX:-UseSHA1Intrinsics
 *                   -XX:-UseSHA256Intrinsics -XX:-UseSHA512Intrinsics
 *                   -Dalgorithm=MD5
 *                   compiler.intrinsics.sha.sanity.TestMD5MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=positive_def.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.MD5::*
 *                   -XX:+UseMD5Intrinsics -Dalgorithm=MD5
 *                   compiler.intrinsics.sha.sanity.TestMD5MultiBlockIntrinsics
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI -Xbatch -XX:CompileThreshold=500
 *                   -XX:Tier4InvocationThreshold=500
 *                   -XX:+LogCompilation -XX:LogFile=negative.log
 *                   -XX:CompileOnly=sun.security.provider.DigestBase::*
 *                   -XX:CompileOnly=sun.security.provider.MD5::*
 *                   -Dalgorithm=MD5
 *                   compiler.intrinsics.sha.sanity.TestMD5MultiBlockIntrinsics
 * @run main/othervm -DverificationStrategy=VERIFY_INTRINSIC_USAGE
 *                   compiler.testlibrary.intrinsics.Verifier positive.log positive_def.log
 *                   negative.log
 */

package compiler.intrinsics.sha.sanity;
import compiler.testlibrary.sha.predicate.IntrinsicPredicates;

public class TestMD5MultiBlockIntrinsics {
    public static void main(String args[]) throws Exception {
        new DigestSanityTestBase(IntrinsicPredicates.isMD5IntrinsicAvailable(),
                DigestSanityTestBase.MB_INTRINSIC_ID).test();
    }
}
