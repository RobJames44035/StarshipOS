/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package com.sun.media.sound;

import java.util.Objects;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.spi.MidiDeviceProvider;

/**
 * Provider for RealTimeSequencer objects.
 *
 * @author Florian Bomers
 */
public final class RealTimeSequencerProvider extends MidiDeviceProvider {

    @Override
    public MidiDevice.Info[] getDeviceInfo() {
        return new MidiDevice.Info[]{RealTimeSequencer.info};
    }

    @Override
    public MidiDevice getDevice(final MidiDevice.Info info) {
        Objects.requireNonNull(info);
        if (RealTimeSequencer.info.equals(info)) {
            return new RealTimeSequencer();
        }
        throw MidiUtils.unsupportedDevice(info);
    }
}
