/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public class LockDuringDumpApp {
    static String LITERAL = "@@LockDuringDump@@LITERAL"; // must be the same as in LockDuringDumpAgent

    public static void main(String args[]) {
        synchronized (LITERAL) { // See comments in LockDuringDumpAgent.java
            System.out.println("I am able to lock the literal string \"" + LITERAL + "\"");
        }
    }
}
