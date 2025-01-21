/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8027751
 * @summary C1 crashes generating G1 post-barrier in Unsafe.getAndSetReference() intrinsic because of the new value spill
 * @requires vm.gc.G1
 *
 * @run main/othervm -XX:+UseG1GC compiler.regalloc.C1ObjectSpillInLogicOp
 */

package compiler.regalloc;

import java.util.concurrent.atomic.AtomicReferenceArray;

/*
 * G1 barriers use logical operators (xor) on T_OBJECT mixed with T_LONG or T_INT.
 * The current implementation of logical operations on x86 in C1 doesn't allow for long operands to be on stack.
 * There is a special code in the register allocator that forces long arguments in registers on x86. However T_OBJECT
 * can be spilled just fine, and in that case the xor emission will fail.
 */
public class C1ObjectSpillInLogicOp {
    public static void main(String[] args) {
        AtomicReferenceArray<Integer> x = new AtomicReferenceArray(128);
        Integer y = new Integer(0);
        for (int i = 0; i < 50000; i++) {
            x.getAndSet(i % x.length(), y);
        }
    }
}
