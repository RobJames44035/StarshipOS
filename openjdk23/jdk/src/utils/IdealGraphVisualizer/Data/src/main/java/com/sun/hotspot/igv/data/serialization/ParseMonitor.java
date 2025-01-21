/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package com.sun.hotspot.igv.data.serialization;

public interface ParseMonitor {

    void updateProgress();

    void setState(String state);

}
