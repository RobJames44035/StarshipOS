/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.internal.vm;

public final class StackChunk {
    public static void init() {}

    private StackChunk parent;
    private int size;    // in words
    private int sp;      // in words
    private int bottom;  // in words

    // The stack itself is appended here by the VM, as well as some injected fields

    public StackChunk parent() { return parent; }
    public boolean isEmpty()   { return sp == bottom; }
}
