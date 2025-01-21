/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.c2.irTests.scalarReplacement;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8333334
 * @summary Tests that dead barrier control flows do not affect the scalar replacement.
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @requires vm.gc.G1
 * @run driver compiler.c2.irTests.scalarReplacement.ScalarReplacementWithGCBarrierTests
 */
public class ScalarReplacementWithGCBarrierTests {
    static class List {
        public Node head;

        public void push(int value) {
            Node n = new Node();
            n.value = value;
            n.next = head;
            head = n;
        }

        @ForceInline
        public Iter iter() {
            Iter iter = new Iter();
            iter.list = this;
            iter.n = head;
            iter.sum = 0;
            return iter;
        }
    }

    static class Node {
        public int value;
        public Node next;
    }

    static class Iter {
        public List list;
        public Node n;
        public Integer sum;

        @ForceInline
        public boolean next() {
            int lastSum = sum;
            while (sum - lastSum < 1000) {
                while (n != null && n.value < 30) n = n.next;
                if (n == null) return false;
                sum += n.value;
                n = n.next;
            }
            return true;
        }
    }

    private static final int SIZE = 1000;

    public static void main(String[] args) {
        // Must use G1 GC to ensure there is a pre-barrier
        // before the first field write.
        TestFramework.runWithFlags("-XX:+UseG1GC");
    }

    @Run(test = "testScalarReplacementWithGCBarrier")
    private void runner() {
        List list = new List();
        for (int i = 0; i < SIZE; i++) {
            list.push(i);
        }
        testScalarReplacementWithGCBarrier(list);
    }

    // Allocation of `Iter iter` should be eliminated by scalar replacement, and
    // the allocation of `Integer sum` can not be eliminated, so there should be
    // 1 allocation after allocations and locks elimination.
    //
    // Before the patch of JDK-8333334, both allocations of `Iter` and `Integer`
    // could not be eliminated.
    @Test
    @IR(phase = { CompilePhase.AFTER_PARSING }, counts = { IRNode.ALLOC, "1" })
    @IR(phase = { CompilePhase.INCREMENTAL_BOXING_INLINE }, counts = { IRNode.ALLOC, "2" })
    @IR(phase = { CompilePhase.ITER_GVN_AFTER_ELIMINATION }, counts = { IRNode.ALLOC, "1" })
    private int testScalarReplacementWithGCBarrier(List list) {
        Iter iter = list.iter();
        while (true) {
            while (iter.next()) {}
            if (list.head == null) break;
            list.head = list.head.next;
            iter.n = list.head;
        }
        return iter.sum;
    }
}
