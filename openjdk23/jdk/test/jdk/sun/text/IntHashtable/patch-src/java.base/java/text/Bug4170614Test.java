/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/* @test
 * @bug 4170614
 * @summary Test internal hashCode() and equals() functions
 * @library ../../../../patch-src
 * @build java.base/java.text.Bug4170614Test
 * @run main java.base/java.text.Bug4170614Test
 */

package java.text;
import sun.text.IntHashtable;


/**
 * This class tests some internal hashCode() functions.
 * Bug #4170614 complained that we had two internal classes that
 * break the invariant that if a.equals(b) than a.hashCode() ==
 * b.hashCode().  This is because these classes overrode equals()
 * but not hashCode().  These are both purely internal classes, and
 * the library itself doesn't actually call hashCode(), so this isn't
 * actually causing anyone problems yet.  But if these classes are
 * ever exposed in the API, their hashCode() methods need to work right.
 * PatternEntry will never be exposed in the API, but IntHashtable
 * might be.
 * @author Richard Gillam
 */
public class Bug4170614Test {
    public static void main(String[] args) throws Exception {
        testIntHashtable();
        testPatternEntry();
    }


    public static void testIntHashtable() throws Exception {
        IntHashtable fred = new IntHashtable();
        fred.put(1, 10);
        fred.put(2, 20);
        fred.put(3, 30);

        IntHashtable barney = new IntHashtable();
        barney.put(1, 10);
        barney.put(3, 30);
        barney.put(2, 20);

        IntHashtable homer = new IntHashtable();
        homer.put(3, 30);
        homer.put(1, 10);
        homer.put(7, 900);

        if (fred.equals(barney)) {
            System.out.println("fred.equals(barney)");
        }
        else {
            System.out.println("!fred.equals(barney)");
        }
        System.out.println("fred.hashCode() == " + fred.hashCode());
        System.out.println("barney.hashCode() == " + barney.hashCode());

        if (!fred.equals(barney)) {
            throw new Exception("equals() failed on two hashtables that are equal");
        }

        if (fred.hashCode() != barney.hashCode()) {
           throw new Exception("hashCode() failed on two hashtables that are equal");
        }

        System.out.println();
        if (fred.equals(homer)) {
            System.out.println("fred.equals(homer)");
        }
        else {
            System.out.println("!fred.equals(homer)");
        }
        System.out.println("fred.hashCode() == " + fred.hashCode());
        System.out.println("homer.hashCode() == " + homer.hashCode());

        if (fred.equals(homer)) {
            throw new Exception("equals() failed on two hashtables that are not equal");
        }

        if (fred.hashCode() == homer.hashCode()) {
            throw new Exception("hashCode() failed on two hashtables that are not equal");
        }

        System.out.println();
        System.out.println("testIntHashtable() passed.\n");
    }

    public static void testPatternEntry() throws Exception {
        PatternEntry fred = new PatternEntry(1,
                                             new StringBuilder("hello"),
                                             new StringBuilder("up"));
        PatternEntry barney = new PatternEntry(1,
                                               new StringBuilder("hello"),
                                               new StringBuilder("down"));
        // (equals() only considers the "chars" field, so fred and barney are equal)
        PatternEntry homer = new PatternEntry(1,
                                              new StringBuilder("goodbye"),
                                              new StringBuilder("up"));

        if (fred.equals(barney)) {
            System.out.println("fred.equals(barney)");
        }
        else {
            System.out.println("!fred.equals(barney)");
        }
        System.out.println("fred.hashCode() == " + fred.hashCode());
        System.out.println("barney.hashCode() == " + barney.hashCode());

        if (!fred.equals(barney)) {
            throw new Exception("equals() failed on two hashtables that are equal");
        }

        if (fred.hashCode() != barney.hashCode()) {
           throw new Exception("hashCode() failed on two hashtables that are equal");
        }

        System.out.println();
        if (fred.equals(homer)) {
            System.out.println("fred.equals(homer)");
        }
        else {
            System.out.println("!fred.equals(homer)");
        }
        System.out.println("fred.hashCode() == " + fred.hashCode());
        System.out.println("homer.hashCode() == " + homer.hashCode());

        if (fred.equals(homer)) {
            throw new Exception("equals() failed on two hashtables that are not equal");
        }

        if (fred.hashCode() == homer.hashCode()) {
            throw new Exception("hashCode() failed on two hashtables that are not equal");
        }

        System.out.println();
        System.out.println("testPatternEntry() passed.\n");
    }
}
