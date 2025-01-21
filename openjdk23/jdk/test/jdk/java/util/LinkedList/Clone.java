/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug     4216997
 * @summary Cloning a subclass of LinkedList results in an object that isn't
 *          an instance of the subclass.  The same applies to TreeSet and
 *          TreeMap.
 */

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Clone {
    public static void main(String[] args) {
        LinkedList2 l = new LinkedList2();
        LinkedList2 lClone = (LinkedList2) l.clone();
        if (!(l.equals(lClone) && lClone.equals(l)))
            throw new RuntimeException("LinkedList.clone() is broken 1.");
        l.add("a");
        lClone = (LinkedList2) l.clone();
        if (!(l.equals(lClone) && lClone.equals(l)))
            throw new RuntimeException("LinkedList.clone() is broken 2.");
        l.add("b");
        lClone = (LinkedList2) l.clone();
        if (!(l.equals(lClone) && lClone.equals(l)))
            throw new RuntimeException("LinkedList.clone() is broken 2.");


        TreeSet2 s = new TreeSet2();
        TreeSet2 sClone = (TreeSet2) s.clone();
        if (!(s.equals(sClone) && sClone.equals(s)))
            throw new RuntimeException("TreeSet.clone() is broken.");
        s.add("a");
        sClone = (TreeSet2) s.clone();
        if (!(s.equals(sClone) && sClone.equals(s)))
            throw new RuntimeException("TreeSet.clone() is broken.");
        s.add("b");
        sClone = (TreeSet2) s.clone();
        if (!(s.equals(sClone) && sClone.equals(s)))
            throw new RuntimeException("TreeSet.clone() is broken.");

        TreeMap2 m = new TreeMap2();
        TreeMap2 mClone = (TreeMap2) m.clone();
        if (!(m.equals(mClone) && mClone.equals(m)))
            throw new RuntimeException("TreeMap.clone() is broken.");
        m.put("a", "b");
        mClone = (TreeMap2) m.clone();
        if (!(m.equals(mClone) && mClone.equals(m)))
            throw new RuntimeException("TreeMap.clone() is broken.");
        m.put("c", "d");
        mClone = (TreeMap2) m.clone();
        if (!(m.equals(mClone) && mClone.equals(m)))
            throw new RuntimeException("TreeMap.clone() is broken.");
    }

    private static class LinkedList2 extends LinkedList {}
    private static class TreeSet2    extends TreeSet {}
    private static class TreeMap2    extends TreeMap {}
}
