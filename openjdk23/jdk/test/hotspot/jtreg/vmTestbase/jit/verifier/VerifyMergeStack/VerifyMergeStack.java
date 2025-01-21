/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/verifier/VerifyMergeStack.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @compile MergeStack.jasm
 * @run main/othervm jit.verifier.VerifyMergeStack.VerifyMergeStack
 */

package jit.verifier.VerifyMergeStack;

import nsk.share.TestFailure;

/**
 * @(#)VerifyMergeStack.java      1.1 01/25/01
 * @bug 4336916
 * @summary Make sure verifier fails when two distinct types meet on operand stack
 */

public class VerifyMergeStack {
    public static void main(String[] args) throws Exception {
            try {
                MergeStack.test();
                throw new TestFailure("shouldn't successfullly verify MergeStack");
            } catch (VerifyError e) {
            }
    }
}
