/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jfr.jvm;

import jdk.jfr.Registered;

// Class used by TestGetEventWriter
@Registered(false)
public class MyCommitRegisteredFalseEvent extends E implements Runnable {
    public void myCommit() {
        PlaceholderEventWriter.getEventWriter();
        throw new RuntimeException("Should not reach here");
    }

    @Override
    public void run() {
        myCommit();
    }
}