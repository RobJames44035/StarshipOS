/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug     8161558
 * @summary ListIterator should not discard cause on exception
 * @run testng CheckForIndexOutOfBoundsException
 */

import java.util.List;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

// Fixed size list containing two elements

class MyList extends AbstractList<String> {

    private static final int SIZE = 2;

    public String get(int i) {
        if (i >= 0 && i < SIZE) {
            return "x";
        } else {
            throw new IndexOutOfBoundsException(i);
        }
    }

    public int size() {
        return SIZE;
    }
}

@Test
public class CheckForIndexOutOfBoundsException {

    List<String> list = new MyList();


    @Test
    public void checkIteratorNext() {
        var iterator = list.iterator(); // position at start
        try {
            for (int i = 0; i <= list.size(); i++) {
                iterator.next();
            }
            fail("Failing checkIteratorNext() - NoSuchElementException should have been thrown");
        } catch (NoSuchElementException e) {
            checkAssertOnException(e);
        }
    }

    @Test
    public void checkListIteratorNext() {
        var iterator = list.listIterator(list.size()); // position at end
        try {
            iterator.next();
            fail("Failing checkListIteratorNext() - NoSuchElementException should have been thrown");
        } catch (NoSuchElementException e) {
            checkAssertOnException(e);
        }
    }

    @Test
    public void checkListIteratorPrevious() {
        var iterator = list.listIterator(0); // position at start
        try {
            iterator.previous();
            fail("Failing checkListIteratorPrevious() - NoSuchElementException should have been thrown");
        } catch (NoSuchElementException e) {
            checkAssertOnException(e);
        }
    }

    private void checkAssertOnException(NoSuchElementException e) {
        var cause = e.getCause();
        assertNotNull(cause, "Exception.getCause()");
        assertTrue(cause instanceof IndexOutOfBoundsException, "Exception.getCause() should be an " +
                "IndexOutOfBoundsException");
        var msg = e.getMessage();
        assertNotNull(msg, "Exception.getMessage()");
        assertTrue(msg.contains("IndexOutOfBoundsException"), "Exception.getMessage() should " +
                "contain the string 'IndexOutOfBoundsException'");
    }
}

