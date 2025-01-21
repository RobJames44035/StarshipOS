/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package com.sun.media.sound;

import java.io.IOException;

/**
 * Resampler stream interface.
 *
 * @author Karl Helgason
 */
public interface SoftResamplerStreamer extends ModelOscillatorStream {

    void open(ModelWavetable osc, float outputsamplerate) throws IOException;
}
