/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug     4468802
 * @summary Submap clear tickled a bug in an optimization suggested by
 *          Prof. William Collins (Lafayette College)
 * @author  Josh Bloch
 */

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SubMapClear {
    public static void main(String[] args) {
        SortedSet treeSet = new TreeSet();
        for (int i = 1; i <=10; i++)
            treeSet.add(new Integer(i));
        Set subSet = treeSet.subSet(new Integer(4),new Integer(10));
        subSet.clear();  // Used to throw exception

        int[] a = { 1, 2, 3, 10 };
        Set s = new TreeSet();
        for (int i = 0; i < a.length; i++)
            s.add(new Integer(a[i]));
        if (!treeSet.equals(s))
            throw new RuntimeException(treeSet.toString());
    }
}
