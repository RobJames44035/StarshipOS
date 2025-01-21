/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4881397
 * @summary generics: verify error when redundant parens used!
 * @author gafter
 *
 * @compile  ParenVerify.java
 * @run main ParenVerify
 */

import java.util.*;

public class ParenVerify {

    public static void main(String argss[]) {
        for(Iterator<Integer> i  = test("Foo"); i.hasNext(); )
            System.out.println(i.next());
    }
    static HashMap<String, LinkedList<Integer>> m
        = new HashMap<String, LinkedList<Integer>>();

    public static Iterator<Integer> test(String s) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        lst.add(new Integer(12));
        m.put("Hello", lst);

        // jsr14-1.3ea generates code that won't verify.
        // Removing the extra set of parenthesis in the
        // statement below fixes the problem
        lst = (m.get("Hello"));
        return lst.iterator();
    }
}
