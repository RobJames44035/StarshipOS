/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @test
 * @bug 8135160
 */
public final class EndlessLoopHugeLengthWave {

    // some data wich can cause an endless loop in RiffReader.java
    private static byte[] headerWAV = {0x52, 0x49, 0x46, 0x46, // RIFF_MAGIC
            0x7, 0xF, 0xF, 0xF, // fileLength
            0x57, 0x41, 0x56, 0x45, //  waveMagic
            0x66, 0x6d, 0x74, 0x20, // FMT_MAGIC
            1, 2, 3, 4, // format
            3, 0,// wav_type  WAVE_FORMAT_IEEE_FLOAT
            1, 0, // channels
            1, 1, // sampleRate
            1, 0, 0, 0, // avgBytesPerSec
            0, 1, // blockAlign
            1, 0, // sampleSizeInBits
            0x64, 0x61, 0x74, 0x61, // DATA_MAGIC
    };

    public static void main(final String[] args) throws Exception {
        try {
            AudioSystem.getAudioFileFormat(new ByteArrayInputStream(headerWAV));
        } catch (final UnsupportedAudioFileException ignored) {
            // Expected
        }
    }
}