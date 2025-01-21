/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4375048
 * @summary AbstractList's ListIterator.hasNext() returns
 *          true, after ListIterator.previous() causes
 *          an exception for an empty list.
 * @author Konstantin Kladko
 */

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class HasNextAfterException {
    public static void main(String[] args) {
        List list = new ArrayList();
        ListIterator i = list.listIterator();
        try {
        i.previous();
        }
        catch (NoSuchElementException e) {
        }
        if (i.hasNext()) {
            throw new RuntimeException(
               "ListIterator.hasNext() returns true for an empty "
                + "List after ListIterator.previous().");
        }
    }
}
