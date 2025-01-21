/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 5020931 8048207
 * @summary Unit test for Collections.checkedQueue
 * @run testng CheckedQueue
 */

import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.testng.annotations.Test;
import static org.testng.Assert.fail;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;


public class CheckedQueue {

    /**
     * This test adds items to a queue.
     */
    @Test
    public void testAdd() {
        int arrayLength = 10;
        Queue<String> abq = Collections.checkedQueue(new ArrayBlockingQueue<>(arrayLength), String.class);

        for (int i = 0; i < arrayLength; i++) {
            abq.add(Integer.toString(i));
        }

        try {
            abq.add("full");
        } catch (IllegalStateException full) {

        }
    }

    /**
     * This test tests the CheckedQueue.add method.  It creates a queue of
     * {@code String}s gets the checked queue, and attempt to add an Integer to
     * the checked queue.
     */
    @Test(expectedExceptions = ClassCastException.class)
    public void testAddFail1() {
        int arrayLength = 10;
        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue(arrayLength + 1);

        for (int i = 0; i < arrayLength; i++) {
            abq.add(Integer.toString(i));
        }

        Queue q = Collections.checkedQueue(abq, String.class);
        q.add(0);
    }

    /**
     * This test tests the CheckedQueue.add method.  It creates a queue of one
     * {@code String}, gets the checked queue, and attempt to add an Integer to
     * the checked queue.
     */
    @Test(expectedExceptions = ClassCastException.class)
    public void testAddFail2() {
        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue(1);
        Queue q = Collections.checkedQueue(abq, String.class);

        q.add(0);
    }

    /**
     * This test tests the Collections.checkedQueue method call for nulls in
     * each and both of the parameters.
     */
    @Test
    public void testArgs() {
        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue(1);
        Queue q;

        try {
            q = Collections.checkedQueue(null, String.class);
            fail( "should throw NullPointerException.");
        } catch(NullPointerException npe) {
            // Do nothing
        }

        try {
            q = Collections.checkedQueue(abq, null);
            fail( "should throw NullPointerException.");
        } catch(Exception e) {
            // Do nothing
        }

        try {
            q = Collections.checkedQueue(null, null);
            fail( "should throw NullPointerException.");
        } catch(Exception e) {
            // Do nothing
        }
    }

    /**
     * This test tests the CheckedQueue.offer method.
     */
    @Test
    public void testOffer() {
        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue(1);
        Queue q = Collections.checkedQueue(abq, String.class);

        try {
            q.offer(null);
            fail("should throw NullPointerException.");
        } catch (NullPointerException npe) {
            // Do nothing
        }

        try {
            q.offer(0);
            fail("should throw ClassCastException.");
        } catch (ClassCastException cce) {
            // Do nothing
        }

        assertTrue(q.offer("0"), "queue should have room");

        // no room at the inn!
        assertFalse(q.offer("1"), "queue should be full");
    }
}
