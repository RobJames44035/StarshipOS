/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jfr.jvm;

// Class used by TestGetEventWriter
public class NonEvent implements Runnable {
    public void commit() {
        PlaceholderEventWriter ew = PlaceholderEventWriter.getEventWriter();;
        throw new RuntimeException("Should not reach here " + ew);
    }

    @Override
    public void run() {
        commit();
    }
}