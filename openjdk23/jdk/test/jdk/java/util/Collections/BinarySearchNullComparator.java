/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4528331 5006032
 * @summary Test Collections.binarySearch() with a null comparator
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinarySearchNullComparator {
    public static void main(String[] args) throws Exception {
        List list = Arrays.asList(new String[] {"I", "Love", "You"});

        int result = Collections.binarySearch(list, "You", null);
        if (result != 2)
            throw new Exception("Result: " + result);
    }
}
