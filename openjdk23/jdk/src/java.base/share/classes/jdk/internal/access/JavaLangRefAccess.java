/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.internal.access;

import java.lang.ref.ReferenceQueue;

public interface JavaLangRefAccess {

    /**
     * Starts the Finalizer and Reference Handler threads.
     */
    void startThreads();

    /**
     * Wait for progress in {@link java.lang.ref.Reference}
     * processing.  If there aren't any pending {@link
     * java.lang.ref.Reference}s, return immediately.
     *
     * @return {@code true} if there were any pending
     * {@link java.lang.ref.Reference}s, {@code false} otherwise.
     */
    boolean waitForReferenceProcessing() throws InterruptedException;

    /**
     * Runs the finalization methods of any objects pending finalization.
     *
     * Invoked by Runtime.runFinalization()
     */
    void runFinalization();
}
