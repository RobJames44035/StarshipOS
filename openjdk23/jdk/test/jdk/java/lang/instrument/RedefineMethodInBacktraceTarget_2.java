/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * This is the second version of this class. The first version is in
 * RedefineMethodInBacktraceTarget.java.
 */
public class RedefineMethodInBacktraceTarget {
    public static void methodToRedefine() {
        throw new RuntimeException("Test exception 2");
    }

    public static void callMethodToDelete() {
        throw new RuntimeException("Test exception 2 in callMethodToDelete");
    }
}
