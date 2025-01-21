/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6307455
 * @summary toArray(a) must set "after-end" element to null
 * @author Martin Buchholz
 */

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class ToArray {
    public static void main(String[] args) throws Throwable {
        Collection<Integer> c = new LinkedBlockingQueue<>();
        if (c.toArray(new Integer[]{42})[0] != null)
            throw new Error("should be null");
    }
}
