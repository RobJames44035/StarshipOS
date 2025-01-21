/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.jfr.jvm;

// Purpose of this class is to have something to
// statically link against for TestGetEventWriter.
//
// When the class is loaded "jdk.jfr.jvm.PlaceholderEventWriter"
// will be replaced with "jdk.jfr.internal.event.EventWriter"
public class PlaceholderEventWriter {

    public static PlaceholderEventWriter getEventWriter() {
        return null;
    }

}
