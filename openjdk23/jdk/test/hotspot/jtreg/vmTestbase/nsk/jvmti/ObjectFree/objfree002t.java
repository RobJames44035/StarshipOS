/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


package nsk.jvmti.ObjectFree;

/**
 * This is an auxiliary class intended only for testing
 * ObjectFree events in the agent part of the test.
 */
public class objfree002t {
    /**
     * big dummy field to enhance chance to be GC'ed
     */
    private byte[] dummyFld = new byte[100000];

    public objfree002t() {}
}
