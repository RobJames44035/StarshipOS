/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.internal.vm;

public class StackChunk {
    int i;
    int j;
    String myName;
    public StackChunk() {
        System.out.println("Constructor called");
        myName = "StackChunk";
        i = 55;
        j = 66;
    }
    public void print() {
        System.out.println("My name is " + myName);
    }
    public int getI() { return i; }
}
