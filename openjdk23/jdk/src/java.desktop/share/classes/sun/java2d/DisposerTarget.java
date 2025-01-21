/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.java2d;

/**
 * This is an interface which should be implemented by
 * the classes which use Disposer.
 */
public interface DisposerTarget {
    /**
     * Returns an object which will be
     * used as the referent in the ReferenceQueue
     */
    public Object getDisposerReferent();
}
