/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 * EventObject.java
 *
 * Created on June 3, 2005, 1:27 PM
 */

package nsk.share.jvmti.attach.loader;

/**
 * This is a dummy object which is created by the ClEventHelper class
 * in its static method. It holds a single String,
 * Creating this object will make one more call back event to the
 * (VM) native agent.
 */

public class EventObject {
    private String name = null;
    private byte[] hugeBuffer;

    public EventObject(String name) {
        this.name = name;
    }

    public void setBreakPointHere() {
        System.out.println(" Here Default Break point can be set");
    }

    public void createHeap(int size) {
        hugeBuffer = new byte[size];
    }
    public void unAllocateMem() {
        hugeBuffer = null;
        System.gc();
    }


}
