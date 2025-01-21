/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8236140
 * @requires vm.gc.Serial
 * @summary Tests proper rehashing of a captured volatile field StoreL node when completing it.
 * @run main/othervm -Xbatch -XX:+UseSerialGC -XX:CompileCommand=compileonly,compiler.macronodes.TestCompleteVolatileStore::test
 *                   compiler.macronodes.TestCompleteVolatileStore
 */

package compiler.macronodes;

public class TestCompleteVolatileStore {
    int i1 = 4;

    public void test() {
        /*
         * The store to the volatile field 'l1' (StoreL) of 'a' is captured in the Initialize node of 'a'
         * (i.e. additional input to it) and completed in InitializeNode::complete_stores.
         * Since 'l1' is volatile, the hash of the StoreL is non-zero triggering the hash assertion failure.
         */
        A a = new A();

        // Make sure that the CheckCastPP node of 'a' is used in the input chain of the Intialize node of 'b'
        B b = new B(a);

        // Make sure 'b' is non-scalar-replacable to avoid eliminating all allocations
        B[] arr = new B[i1];
        arr[i1-3] = b;
    }

    public static void main(String[] strArr) {
        TestCompleteVolatileStore _instance = new TestCompleteVolatileStore();
        for (int i = 0; i < 10_000; i++ ) {
            _instance.test();
        }
    }
}

class A {
    // Needs to be volatile to have a non-zero hash for the StoreL node.
    volatile long l1;

    A() {
        // StoreL gets captured and is later processed in InitializeNode::complete_stores while expanding the allocation node.
        this.l1 = 256;
    }
}

class B {
    A a;

    B(A a) {
        this.a = a;
    }
}

