/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 4229892
 * @summary Arrays.fill(Object[], ...) should throw ArrayStoreException
 * @author Martin Buchholz
 */

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class Fill {
    private static void realMain(String[] args) throws Throwable {
        try {
            Arrays.fill(new Integer[3], "foo");
            fail("Expected ArrayStoreException");
        }
        catch (ArrayStoreException e) { pass(); }
        catch (Throwable t) { unexpected(t); }

        try {
            Arrays.fill(new Integer[3], 0, 2, "foo");
            fail("Expected ArrayStoreException");
        }
        catch (ArrayStoreException e) { pass(); }
        catch (Throwable t) { unexpected(t); }

        try {
            Arrays.fill(new Integer[3], 0, 4, "foo");
            fail("Expected ArrayIndexOutOfBoundsException");
        }
        catch (ArrayIndexOutOfBoundsException e) { pass(); }
        catch (Throwable t) { unexpected(t); }
    }

    //--------------------- Infrastructure ---------------------------
    static volatile int passed = 0, failed = 0;
    static void pass() {passed++;}
    static void fail() {failed++; Thread.dumpStack();}
    static void fail(String msg) {System.out.println(msg); fail();}
    static void unexpected(Throwable t) {failed++; t.printStackTrace();}
    static void check(boolean cond) {if (cond) pass(); else fail();}
    static void equal(Object x, Object y) {
        if (x == null ? y == null : x.equals(y)) pass();
        else fail(x + " not equal to " + y);}
    public static void main(String[] args) throws Throwable {
        try {realMain(args);} catch (Throwable t) {unexpected(t);}
        System.out.printf("%nPassed = %d, failed = %d%n%n", passed, failed);
        if (failed > 0) throw new AssertionError("Some tests failed");}
}
