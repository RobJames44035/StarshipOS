/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * Unit tests for JVMTI IterateOverReachableObjects and
 * IterateOverObjectsReachableFromObject functions.
 *
 */

package nsk.jvmti.unit.heap;

import nsk.share.jvmti.unit.*;
import java.io.PrintStream;

public class HeapWalkTests {

    final static int JCK_STATUS_BASE = 95;
    final static int PASSED = 0;
    final static int FAILED = 2;

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        test1();
        test2();
        test3();
        return PASSED;
    }

    private static void test1() {
        long tag;

        // This test uses the heap_root_callback to tag all the roots

        // It then examines a few known roots and checks that they
        // are tagged.

        // create a global reference
        Object o = new Object();
        Heap.newGlobalRef(o);

        // make sure current thread isn't tagged
        Heap.setTag(Thread.currentThread(), 0);

        Heap.setHeapRootCallback();
        Heap.iterateOverReachableObjects();

        // o must be JNI global - there's no other way it can be a root

        tag = Heap.getTag(o);

        if (tag != Heap.JVMTI_HEAP_ROOT_JNI_GLOBAL) {
            throw new RuntimeException("JNI global should have been tagged");
        }

        // Current thread is root but can't assume it will be
        // tagged with JVMTI_HEAP_ROOT_JNI_GLOBAL

        tag = Heap.getTag(Thread.currentThread());
        if (tag == 0) {
            throw new RuntimeException("Current thread isn't tagged");
        }
    }

    private static void test2() {
        long tag;

        // This test uses the stack_ref_callback to tag all references
        // from the thread stacks. The callback tags all objects with the tag
        // of the thread.

        Object o = new Object();

        Heap.setTag(Thread.currentThread(), 888);

        Heap.setStackRefCallback();
        Heap.iterateOverReachableObjects();

        tag = Heap.getTag(o);
        if (tag != 888) {
            throw new RuntimeException("stack local not tagged correctly");
        }
    }


    // used by test3
    static class Foo {
        private Object fld;

        Foo() {
            fld = new Object();
        }

        Object field() {
            return fld;
        }

        public static Object static_field = new Object();
    }

    private static int failures = 0;

    private static void check(Object o, long tag) {
        long actual_tag = Heap.getTag(o);
        if (actual_tag != tag) {
            if (actual_tag == 0) {
                System.err.println(o + " is not tagged!");
            } else {
                System.err.println(o + " is incorrectly tagged");
            }
            failures++;
        }
    }

    private static void test3() {
        long tag;

        // This test tags an object, and then calls IterateOverObjectsReachableFromObject
        // The callback tags all objects with a tag value of 777.

        Foo foo = new Foo();

        Heap.setObjectRefCallback();
        Heap.iterateOverObjectsReachableFromObject(foo);

        check(Foo.class, 777);
        check(Foo.static_field, 777);
        check(foo.field(), 777);

        if (failures > 0) {
            throw new RuntimeException("IterateOverObjectsReachableFromObject test failed");
        }
    }
}
