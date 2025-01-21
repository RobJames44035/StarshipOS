/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

/**
 * Marker interface to signify that run params are needed.
 */
public interface GCParamsAware {
        public void setGCParams(GCParams gcParams);
}
