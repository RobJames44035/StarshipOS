/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test SoftTuning constructor
   @modules java.desktop/com.sun.media.sound
*/

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class NewSoftTuning {

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
        SoftTuning tuning = new SoftTuning();
        double[] tunings = tuning.getTuning();
        for (int i = 0; i < tunings.length; i++) {
            assertTrue(Math.abs(tunings[i]-i*100) < 0.00001);
        }
    }
}
