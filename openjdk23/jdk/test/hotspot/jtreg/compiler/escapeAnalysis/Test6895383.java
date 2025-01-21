/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6895383
 * @summary JCK test throws NPE for method compiled with Escape Analysis
 *
 * @run main/othervm -Xcomp compiler.escapeAnalysis.Test6895383
 */

package compiler.escapeAnalysis;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test6895383 {
    public static void main(String argv[]) {
        Test6895383 test = new Test6895383();
        test.testRemove1_IndexOutOfBounds();
        test.testAddAll1_IndexOutOfBoundsException();
    }

    public void testRemove1_IndexOutOfBounds() {
        CopyOnWriteArrayList c = new CopyOnWriteArrayList();
    }

    public void testAddAll1_IndexOutOfBoundsException() {
        try {
            CopyOnWriteArrayList c = new CopyOnWriteArrayList();
            c.addAll(-1, new LinkedList()); // should throw IndexOutOfBoundsException
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
