/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
 * @bug 4076283
 * @summary java.lang.ref.SoftReference should reliably prevent
 *          OutOfMemoryErrors
 * @author Peter Jones
 * @author Mark Reinhold
 */

/* If this test fails, an OutOfMemoryError will be thrown */

import java.lang.ref.SoftReference;


public class Bash {

    static class TestReference extends SoftReference {

        public static TestReference head;
        public TestReference next;

        public TestReference(Object referent) {
            super(referent);
            next = head;
            head = this;
        }
    }


    static final int NUM_BLOCKS = 2048;
    static final int BLOCK_SIZE = 32768;


    public static void main(String[] args) throws Exception {

        for (int i = 0; i < NUM_BLOCKS; ++ i) {
            TestReference ref = new TestReference(new byte[BLOCK_SIZE]);
        }

        int emptyCount = 0;
        int fullCount = 0;
        for (TestReference r = TestReference.head; r != null; r = r.next) {
            if (r.get() == null) emptyCount++;
            else fullCount++;
        }

        System.err.println(fullCount + " full, " + emptyCount + " empty ");

    }

}
