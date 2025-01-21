/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test SoftLimiter processAudio method
   @modules java.desktop/com.sun.media.sound
*/

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class ProcessAudio_replace_overdrive_mono {

    private static void assertEquals(Object a, Object b) throws Exception
    {
        if(!a.equals(b))
            throw new RuntimeException("assertEquals fails!");
    }

    private static void assertTrue(boolean value) throws Exception
    {
        if(!value)
            throw new RuntimeException("assertTrue fails!");
    }

    public static void main(String[] args) throws Exception {
        SoftSynthesizer synth = new SoftSynthesizer();
        synth.openStream(new AudioFormat(44100, 16, 1, true, false), null);

        SoftAudioBuffer in1 = new SoftAudioBuffer(250, synth.getFormat());
        SoftAudioBuffer out1 = new SoftAudioBuffer(250, synth.getFormat());

        float[] testdata1 = new float[in1.getSize()];
        float[] n1a = in1.array();
        float[] out1a = out1.array();
        for (int i = 0; i < n1a.length; i++) {
            testdata1[i] = (float)Math.sin(i*0.3)*2.5f;
            n1a[i] = testdata1[i];
            out1a[i] = 1;
        }

        SoftLimiter limiter = new SoftLimiter();
        limiter.init(44100, 147);
        limiter.setMixMode(false);
        limiter.setInput(0, in1);
        limiter.setOutput(0, out1);
        limiter.processControlLogic();
        limiter.processAudio();
        limiter.processControlLogic();
        limiter.processAudio();
        // Limiter should delay audio by one buffer,
        // and there should almost no different in output v.s. input
        for (int i = 0; i < n1a.length; i++) {
            if(Math.abs(out1a[i]) > 1.0)
                throw new Exception("abs(output)>1");
        }

        synth.close();
    }
}
