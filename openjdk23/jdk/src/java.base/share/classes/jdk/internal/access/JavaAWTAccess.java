/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package jdk.internal.access;

public interface JavaAWTAccess {

    // Returns the AppContext used for applet logging isolation, or null if
    // no isolation is required.
    // If there's no applet, or if the caller is a stand alone application,
    // or running in the main app context, returns null.
    // Otherwise, returns the AppContext of the calling applet.
    public Object getAppletContext();
}
